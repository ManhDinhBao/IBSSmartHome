package com.ibs.android.ibssmarthome.Adapter;

import android.content.Context;
import android.graphics.Point;
import androidx.annotation.NonNull;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibs.android.ibssmarthome.Object.RoomObject;
import com.ibs.android.ibssmarthome.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.RoomsViewHolder> {
    private Context mContext;
    private ArrayList<RoomObject> mItemList;
    private OnItemClickListener mListener;

    public RoomsAdapter(Context context, ArrayList<RoomObject> itemList)
    {
        mContext=context;
        mItemList=itemList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemClick(int position,TextView roomName, ImageView roomBG);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public RoomsAdapter.RoomsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_room,viewGroup,false);
        v.getLayoutParams().height = (getScreenHeiht() / 3);
        return new RoomsViewHolder(v);
    }

    public int getScreenHeiht() {

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.y;
    }


    @Override
    public void onBindViewHolder(@NonNull RoomsAdapter.RoomsViewHolder roomsViewHolder, int i) {
        RoomObject room=mItemList.get(i);

        String roomName = room.getName();
        String roomType = room.getType().getID();

        String roomBG = room.getWallPicture();
        //int intRoomBG = Integer.parseInt(roomBG);

        Picasso.get().load(roomBG).fit().centerCrop().placeholder(R.drawable.living_room).into(roomsViewHolder.mImgRoomBG);

        switch (roomType){
            case "RT00000001":Picasso.get().load(R.drawable.living_room).fit().centerCrop().placeholder(R.drawable.living_room).into(roomsViewHolder.mImgRoomBG);
                break;
            case "RT00000002":Picasso.get().load(R.drawable.bedroom).fit().centerCrop().placeholder(R.drawable.bedroom).into(roomsViewHolder.mImgRoomBG);
                break;
            case "RT00000003":Picasso.get().load(R.drawable.bathroom).fit().centerCrop().placeholder(R.drawable.bathroom).into(roomsViewHolder.mImgRoomBG);
                break;
            case "RT00000004":Picasso.get().load(R.drawable.kitchen).fit().centerCrop().placeholder(R.drawable.kitchen).into(roomsViewHolder.mImgRoomBG);
                break;
            case "RT00000005":Picasso.get().load(R.drawable.balcony).fit().centerCrop().placeholder(R.drawable.balcony).into(roomsViewHolder.mImgRoomBG);
                break;
        }

        roomsViewHolder.mTxtRoomName.setText(roomName);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class RoomsViewHolder extends RecyclerView.ViewHolder {

        private TextView mTxtRoomName;
        private ImageView mImgRoomBG;

        public RoomsViewHolder(@NonNull View itemView) {
            super(itemView);

            mTxtRoomName = itemView.findViewById(R.id.txtCardRoomTitle);
            mImgRoomBG   = itemView.findViewById(R.id.imgCardRoomBg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position,mTxtRoomName,mImgRoomBG);
                        }
                    }
                }
            });
        }
    }
}
