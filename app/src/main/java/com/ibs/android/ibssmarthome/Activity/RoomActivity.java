package com.ibs.android.ibssmarthome.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.card.MaterialCardView;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ibs.android.ibssmarthome.Adapter.DeviceAdapter;
import com.ibs.android.ibssmarthome.Comm;
import com.ibs.android.ibssmarthome.Object.DeviceObject;
import com.ibs.android.ibssmarthome.Object.PointObject;
import com.ibs.android.ibssmarthome.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RoomActivity extends Activity {
    private ImageView imgBackground;
    private TextView txtRoomName;
    private String roomID;
    private DeviceAdapter xAdapter;
    private ArrayList<DeviceObject> devices;
    private GridView xGridView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_room);

        roomID = getIntent().getStringExtra("EXTRA_ROOM_ID");
        String roomName = getIntent().getStringExtra("EXTRA_ROOM_NAME");
        String titleImage = getIntent().getStringExtra("EXTRA_ROOM_BG");

        imgBackground = findViewById(R.id.imgRoomDetailBG);
        //imgBackground.setImageResource(titleImage);
        Picasso.get().load(titleImage).fit().centerCrop().placeholder(R.drawable.loading).into(imgBackground);


        txtRoomName = findViewById(R.id.txtRoomDetailName);
        txtRoomName.setText(roomName);

        xGridView = findViewById(R.id.gridDevice);

        loadDevicesInRoom();

    }

    private void loadDevicesInRoom() {
        String Url = Comm.strAPI + "Room/" + roomID;

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, Url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String jsonData = response.toString();

                devices = getDevicesFromJson(jsonData);
                if (devices != null) {
                    try {

                        xAdapter = new DeviceAdapter(RoomActivity.this, devices);
                        xGridView.setAdapter(xAdapter);

                        xGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                vibe.vibrate(100);

                                DeviceObject device = devices.get(position);
                                MaterialCardView materialCardView = (MaterialCardView)view;

                                if (device.isPower()) {
                                    materialCardView.setCardBackgroundColor(ContextCompat.getColor(RoomActivity.this,R.color.lv3Color));
                                    view.setElevation(0);
                                    device.setPower(false);

                                } else {
                                    materialCardView.setCardBackgroundColor(ContextCompat.getColor(RoomActivity.this,R.color.whiteColor));
                                    view.setElevation(4);
                                    device.setPower(true);
                                }

                                xAdapter.notifyDataSetChanged();
                            }
                        });

                        xGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent= new Intent(RoomActivity.this,DevicePropertiesActivity.class);
                                intent.putExtra("EXTRA_DEVICE_ID",devices.get(position).getId());
                                startActivity(intent);
                                return true;
                            }
                        });

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

        Volley.newRequestQueue(this).add(stringRequest);

    }

    private ArrayList<DeviceObject> getDevicesFromJson(String jsonData) {
        try {
            JSONObject ojRoom = new JSONObject(jsonData);

            JSONArray arrDevices = new JSONArray(ojRoom.getString("Devices"));
            ArrayList<DeviceObject> devices = new ArrayList<>();
            for (int i = 0; i < arrDevices.length(); i++) {
                JSONObject ojDevice = arrDevices.getJSONObject(i);

                String deviceId = ojDevice.getString("ID");
                String deviceName = ojDevice.getString("Name");
                String deviceDescr = ojDevice.getString("Descr");
                String deviceType = ojDevice.getString("Type");
                String deviceIconOn = ojDevice.getString("IconOn");
                String deviceIconOff = ojDevice.getString("IconOff");

                JSONArray arrPoints = new JSONArray(ojDevice.getString("Points"));
                ArrayList<PointObject> points = new ArrayList<>();
                for (int j = 0; j < arrPoints.length(); j++) {
                    JSONObject ojPoints = arrPoints.getJSONObject(j);

                    String pointID = ojPoints.getString("ID");
                    String pointName = ojPoints.getString("Name");
                    String pointDescr = ojPoints.getString("Descr");
                    String pointType = ojPoints.getString("Type");
                    String pointAddress = ojPoints.getString("Address");

                    points.add(new PointObject(pointID, pointName, pointDescr, pointType, pointAddress));
                }

                devices.add(new DeviceObject(deviceId, deviceName, deviceDescr, deviceType, deviceIconOn, deviceIconOff, false, points));
            }

            return devices;

        } catch (JSONException ex) {
            Log.e("manh", ex.toString());
            return null;
        }
    }


}
