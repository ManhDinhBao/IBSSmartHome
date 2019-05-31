package com.ibs.android.ibssmarthome.Fragment;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibs.android.ibssmarthome.Activity.RoomActivity;
import com.ibs.android.ibssmarthome.Adapter.RoomsAdapter;
import com.ibs.android.ibssmarthome.Global;
import com.ibs.android.ibssmarthome.Object.RoomObject;
import com.ibs.android.ibssmarthome.R;
import com.ibs.android.ibssmarthome.Utils.ItemOffsetDecoration;
import java.util.ArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class HomeFragment extends Fragment implements RoomsAdapter.OnItemClickListener {
    private RecyclerView            recycleRooms;
    private RoomsAdapter            roomsAdapter;
    private TextView                textViewHomeName;
    private ArrayList<RoomObject>   rooms;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //region Define Control
        recycleRooms = view.findViewById(R.id.recycleview_home);

        textViewHomeName = view.findViewById(R.id.textview_home_name);

        ProgressDialog dialog = ProgressDialog.show(getContext(), "Loading home data", "Please wait...", true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.margin_all_default);
        //endregion

        //region Set Control
        //When not yet get Apartment data
        while (Global.sHome==null){
            Log.d("SmartHome","HomeFragment: Home object null");
            //Show waiting dialog
            dialog.show();
        }
        //When done set control value based on home data
        dialog.dismiss();
        Log.d("SmartHome","HomeFragment: "+Global.sHome.getName());
        rooms = new ArrayList<>();
        rooms = Global.sHome.getRooms();

        roomsAdapter = new RoomsAdapter(getActivity(), rooms);
        roomsAdapter.setOnItemClickListener(HomeFragment.this);

        recycleRooms.setHasFixedSize(true);
        recycleRooms.setLayoutManager(layoutManager);
        recycleRooms.setAdapter(roomsAdapter);
        recycleRooms.addItemDecoration(itemDecoration);

        textViewHomeName.setText(Global.sHome.getName());
        //endregion

        return view;
    }

    @Override
    public void onItemClick(int position) {
    }

    @Override
    public void onItemClick(int position, TextView roomName, ImageView roomBG) {

        //Get selected room by position
        RoomObject r = this.rooms.get(position);
        String roomId = r.getID();
        String roomNm = r.getName();
        String roomPic = r.getWallPicture();
        String roomTypeId = r.getType().getID();

        //Send room info to room activity
        Intent intent = new Intent(getActivity().getApplicationContext(), RoomActivity.class);
        intent.putExtra("EXTRA_ROOM_ID", roomId);
        intent.putExtra("EXTRA_ROOM_TYPE_ID", roomTypeId);
        intent.putExtra("EXTRA_ROOM_NAME", roomNm);
        intent.putExtra("EXTRA_ROOM_BG", roomPic);

        //Pair control
        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View, String>(roomName, "txtTransition");
        pairs[1] = new Pair<View, String>(roomBG, "imgTransition");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pairs);
        startActivity(intent, options.toBundle());
    }

}
