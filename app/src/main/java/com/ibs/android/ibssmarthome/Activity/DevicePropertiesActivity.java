package com.ibs.android.ibssmarthome.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ibs.android.ibssmarthome.Adapter.DevicePropertiesAdapter;
import com.ibs.android.ibssmarthome.Comm;
import com.ibs.android.ibssmarthome.Object.DeviceObject;
import com.ibs.android.ibssmarthome.Object.PointObject;
import com.ibs.android.ibssmarthome.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DevicePropertiesActivity extends AppCompatActivity {

    private ViewPager viewPagerDeviceProperties;
    private TabLayout tabDeviceProperties;
    private DevicePropertiesAdapter adapterViewPager;
    private String deviceId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deviceproperties);

        deviceId = getIntent().getStringExtra("EXTRA_DEVICE_ID");

        viewPagerDeviceProperties = findViewById(R.id.device_properties_viewpager);
        tabDeviceProperties = findViewById(R.id.tab_layout_device_properties);

    }

    private void loadDevice() {
        String Url = Comm.strAPI + "Device/" + deviceId;

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, Url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String jsonData = response.toString();
                ArrayList<Integer> deviceProperties = new ArrayList<>();
                deviceProperties.add(Comm.DEVICE_PROPERTIES_POWER);

                DeviceObject device = getDeviceFromJson(jsonData);
                if (device != null) {
                    try {
                        switch (device.getType()){
                            case Comm.DEVICE_TYPE_LIGHT:
                                deviceProperties.add(Comm.DEVICE_PROPERTIES_VALUE);
                                break;
                            case Comm.DEVICE_TYPE_AC:
                                deviceProperties.add(Comm.DEVICE_PROPERTIES_MODE);
                                deviceProperties.add(Comm.DEVICE_PROPERTIES_VALUE);
                                break;
                            case Comm.DEVICE_TYPE_EW:
                                break;
                            case Comm.DEVICE_TYPE_BUTTON:
                                break;
                            case Comm.DEVICE_TYPE_METER:
                                break;
                            case Comm.DEVICE_TYPE_TV:
                                break;
                            case Comm.DEVICE_TYPE_DOOR:
                                break;
                            case Comm.DEVICE_TYPE_FAN:
                                deviceProperties.add(Comm.DEVICE_PROPERTIES_MODE);
                                break;
                        }


                        adapterViewPager = new DevicePropertiesAdapter(getSupportFragmentManager(), deviceProperties);
                        viewPagerDeviceProperties.setAdapter(adapterViewPager);

                        tabDeviceProperties.setupWithViewPager(viewPagerDeviceProperties, true);
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

    private DeviceObject getDeviceFromJson(String jsonData) {
        try {
            JSONObject ojDevice = new JSONObject(jsonData);

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

            return new DeviceObject(deviceId, deviceName, deviceDescr, deviceType, deviceIconOn, deviceIconOff, true, points);

        } catch (JSONException ex) {
            Log.e("manh", ex.toString());
            return null;
        }
    }
}
