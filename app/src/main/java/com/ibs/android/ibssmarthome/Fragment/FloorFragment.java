package com.ibs.android.ibssmarthome.Fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ibs.android.ibssmarthome.Activity.RoomActivity;
import com.ibs.android.ibssmarthome.Adapter.RoomsAdapter;
import com.ibs.android.ibssmarthome.Comm;
import com.ibs.android.ibssmarthome.Object.DeviceObject;
import com.ibs.android.ibssmarthome.Object.FloorObject;
import com.ibs.android.ibssmarthome.Object.PointObject;
import com.ibs.android.ibssmarthome.Object.RoomObject;
import com.ibs.android.ibssmarthome.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FloorFragment extends Fragment implements RoomsAdapter.OnItemClickListener {

    private String floorId;
    private RecyclerView recycleRooms;
    private RoomsAdapter roomsAdapter;
    private ArrayList<RoomObject> rooms=new ArrayList<>();
    public static FloorFragment newInstance(String floorId) {
        FloorFragment fragFloor = new FloorFragment();
        Bundle args = new Bundle();
        args.putString("floorId", floorId);
        fragFloor.setArguments(args);
        return fragFloor;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.floorId = getArguments().getString("floorId");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_floor, container, false);
        recycleRooms = view.findViewById(R.id.recycleRooms);
        loadFloorData();

        return view;
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemClick(int position, TextView roomName, ImageView roomBG) {

        RoomObject r=this.rooms.get(position);
        String roomId = r.getID();
        String roomNm = r.getName();
        String roomPic =r.getWallPicture();

//        int roomDetailBG = R.drawable.living_room;
//        switch (r.getType()){
//            case "RT00000001":roomDetailBG = R.drawable.living_room;
//                break;
//            case "RT00000002":roomDetailBG=R.drawable.bedroom;
//                break;
//            case "RT00000003":roomDetailBG=R.drawable.bathroom;
//                break;
//            case "RT00000004":roomDetailBG=R.drawable.kitchen;
//                break;
//            case "RT00000005":roomDetailBG=R.drawable.balcony;
//                break;
//        }


        Intent intent =new Intent(getActivity().getApplicationContext(),RoomActivity.class);
        intent.putExtra("EXTRA_ROOM_ID",roomId);
        intent.putExtra("EXTRA_ROOM_NAME",roomNm);
        intent.putExtra("EXTRA_ROOM_BG",roomPic);

        Pair[] pairs =new Pair[2];
        pairs[0] =new Pair<View,String>(roomName,"txtTransition");
        pairs[1] =new Pair<View,String>(roomBG,"imgTransition");
        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(getActivity(),pairs);
        startActivity(intent,options.toBundle());
    }

    private void loadFloorData() {
        String Url = Comm.strAPI + "Floor/" + floorId;

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, Url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String jsonData = response.toString();
                FloorObject floor=getFloorFromJson(jsonData);
                ArrayList<RoomObject> rooms=new ArrayList<>();
                if (floor != null) {
                    try {
                        recycleRooms.setHasFixedSize(true);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        recycleRooms.setLayoutManager(layoutManager);
                        rooms = floor.getRooms();

                        roomsAdapter = new RoomsAdapter(getActivity(), rooms);
                        recycleRooms.setAdapter(roomsAdapter);

                        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
                       // recycleRooms.addItemDecoration(dividerItemDecoration);
                        roomsAdapter.setOnItemClickListener(FloorFragment.this);
                    } catch (Exception ex) {
                        Log.e("manh", ex.toString());
                    }
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(getActivity()).add(stringRequest);

    }

    private FloorObject getFloorFromJson(String jsonData) {
        FloorObject floor=new FloorObject();
        try {

            JSONObject ojFloor = new JSONObject(jsonData);

            String floorID = ojFloor.getString("Id");
            String floorName = ojFloor.getString("Name");
            String floorDescr = ojFloor.getString("Descr");

            JSONArray arrRooms = new JSONArray(ojFloor.getString("Rooms"));
            ArrayList<RoomObject> rooms = new ArrayList<>();
            for (int j = 0; j < arrRooms.length(); j++) {
                JSONObject ojRoom = arrRooms.getJSONObject(j);

                String roomID = ojRoom.getString("ID");
                String roomName = ojRoom.getString("Name");
                String roomType = ojRoom.getString("Type");
                String roomIconPic = ojRoom.getString("IconPicture");
                String roomWallPic = ojRoom.getString("WallPicture");

                JSONArray arrDevices = new JSONArray(ojRoom.getString("Devices"));
                ArrayList<DeviceObject> devices = new ArrayList<>();
                for (int k = 0; k < arrDevices.length(); k++) {
                    JSONObject ojDevices = arrDevices.getJSONObject(k);

                    String deviceID = ojDevices.getString("ID");
                    String deviceName = ojDevices.getString("Name");
                    String deviceDescr = ojDevices.getString("Descr");
                    String deviceType = ojDevices.getString("Type");
                    String deviceIconOn = ojDevices.getString("IconOn");
                    String deviceIconOff = ojDevices.getString("IconOff");

                    JSONArray arrPoints = new JSONArray(ojDevices.getString("Points"));
                    ArrayList<PointObject> points = new ArrayList<>();
                    for (int h = 0; h < arrPoints.length(); h++) {
                        JSONObject ojPoints = arrPoints.getJSONObject(h);

                        String pointID = ojPoints.getString("ID");
                        String pointName = ojPoints.getString("Name");
                        String pointDescr = ojPoints.getString("Descr");
                        String pointType = ojPoints.getString("Type");
                        String pointAddress = ojPoints.getString("Address");

                        points.add(new PointObject(pointID, pointName, pointDescr, pointType, pointAddress));
                    }

                    devices.add(new DeviceObject(deviceID, deviceName, deviceDescr, deviceType, deviceIconOn, deviceIconOff, false, points));
                }

                rooms.add(new RoomObject(roomID, roomName, roomType, roomIconPic, roomWallPic, devices));

                this.rooms=rooms;

                floor.setId(floorID);
                floor.setName(floorName);
                floor.setDescr(floorDescr);
                floor.setRooms(rooms);

            }
            return floor;

        } catch (JSONException ex) {
            Log.e("manh", ex.toString());
            return null;
        }
    }
}
