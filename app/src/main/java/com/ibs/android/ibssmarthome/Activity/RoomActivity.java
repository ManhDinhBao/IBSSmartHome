package com.ibs.android.ibssmarthome.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.ibs.android.ibssmarthome.Adapter.DeviceAdapter;
import com.ibs.android.ibssmarthome.Comm;
import com.ibs.android.ibssmarthome.Global;
import com.ibs.android.ibssmarthome.Object.DeviceObject;
import com.ibs.android.ibssmarthome.Object.RoomObject;
import com.ibs.android.ibssmarthome.Object.TCPMessageObject;
import com.ibs.android.ibssmarthome.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RoomActivity extends AppCompatActivity {
    private ImageView               imgBackground;
    private TextView                txtRoomName;
    private String                  roomID,roomTypeId;
    public static DeviceAdapter     deviceAdapter;
    private ArrayList<DeviceObject> devices;
    private GridView                gvDevices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_room);

        //Get room info from Home fragment
        roomID                  = getIntent().getStringExtra("EXTRA_ROOM_ID");
        String roomName         = getIntent().getStringExtra("EXTRA_ROOM_NAME");
        String titleImage       = getIntent().getStringExtra("EXTRA_ROOM_BG");
        roomTypeId              = getIntent().getStringExtra("EXTRA_ROOM_TYPE_ID");

        //Get room info by id
        RoomObject roomObject = Global.GetRoomById(roomID);
        //Get list devices in room
        devices = roomObject.getDevices();

        //region Control Define
        imgBackground = findViewById(R.id.imageview_room_background);
        txtRoomName = findViewById(R.id.textview_room_roomname);
        gvDevices = findViewById(R.id.gridview_room_device);
        deviceAdapter = new DeviceAdapter(RoomActivity.this, devices);
        //endregion

        //region Control Set
        int roomBackGround;
        //Get background based on room type
        switch (roomTypeId) {
            case Comm.ROOM_TYPE_LIVING:
                roomBackGround = R.drawable.living_room;
                break;
            case Comm.ROOM_TYPE_BED:
                roomBackGround = R.drawable.bedroom;
                break;
            case Comm.ROOM_TYPE_BATH:
                roomBackGround = R.drawable.bathroom;
                break;
            case Comm.ROOM_TYPE_KITCHEN:
                roomBackGround = R.drawable.kitchen;
                break;
            case Comm.ROOM_TYPE_BALCONY:
                roomBackGround = R.drawable.balcony;
                break;
            case Comm.ROOM_TYPE_YARD:
                roomBackGround = R.drawable.yard;
                break;
            default:
                roomBackGround = R.drawable.living_room;
        }

        //Set background
        Picasso.get().load(titleImage).fit().centerCrop().placeholder(roomBackGround).into(imgBackground);

        //Set room name
        txtRoomName.setText(roomName);

        //Set grid view adapter
        gvDevices.setAdapter(deviceAdapter);
        //endregion

        //region Control event
        try {
            gvDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibe.vibrate(100);

                    DeviceObject device = devices.get(position);
                    String powerTopic = device.GetPowerPublicTopic();
                    ArrayList<String> messParams=new ArrayList<>();
                    messParams.add(powerTopic);
                    if (powerTopic!=null) {
                        if (device.isPower()) {
                            device.setPower(false);
                            messParams.add("0");
                        } else {
                            device.setPower(true);
                            messParams.add("1");
                        }
                        TCPMessageObject message = new TCPMessageObject(Comm.MESSAGE_CODE_SEND_POINT,messParams);
                        MainActivity.mTcpClient.SendMessage(message.toString());
                        deviceAdapter.notifyDataSetChanged();
                    }
                }

            });

            gvDevices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(RoomActivity.this, DevicePropertiesActivity.class);
                    intent.putExtra("EXTRA_DEVICE_ID", devices.get(position).getId());
                    intent.putExtra("EXTRA_ROOM_TYPE_ID", roomTypeId);
                    startActivity(intent);
                    return true;
                }
            });


        } catch (Exception ex) {
            Log.e("SmartHome","RoomActivity - GridviewEvent:"+ ex.toString());
        }
        //endregion

    }

}
