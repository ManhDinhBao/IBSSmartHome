package com.ibs.android.ibssmarthome.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import java.util.List;


public class HomeFragment extends Fragment {
    private ViewPager mRoomsViewPager;
    private TabLayout tabRooms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRoomsViewPager = view.findViewById(R.id.room_viewpager);
        tabRooms = view.findViewById(R.id.tab_home_layout);

        loadFloorData();

        return view;
    }
    private void loadFloorData(){
        String Url = Comm.strAPI +"Floor";

        StringRequest stringRequest =new StringRequest(com.android.volley.Request.Method.GET, Url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String jsonData = response.toString();
                ArrayList<FloorObject> floors = getFloorFromJson(jsonData);
                if (floors!=null){
                    try {
                        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
                        for(int i=0;i<floors.size();i++){
                            String floorId = floors.get(i).getId();
                            String floorName = floors.get(i).getName();
                            FloorFragment fragFloor= FloorFragment.newInstance(floorId);
                            adapter.addFragment(fragFloor,floorName);
                        }
                        mRoomsViewPager.setAdapter(adapter);
                        mRoomsViewPager.setOffscreenPageLimit(floors.size());
                        tabRooms.setupWithViewPager(mRoomsViewPager);
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

    private ArrayList<FloorObject> getFloorFromJson(String jsonData){
        try {

            JSONArray arrFloor = new JSONArray(jsonData);
            ArrayList<FloorObject> floors=new ArrayList<>();
            for (int i = 0; i < arrFloor.length(); i++) {
                JSONObject ojFloor = arrFloor.getJSONObject(i);

                String floorID      = ojFloor.getString("Id");
                String floorName    = ojFloor.getString("Name");
                String floorDescr   = ojFloor.getString("Descr");

                JSONArray arrRooms = new JSONArray(ojFloor.getString("Rooms"));
                ArrayList<RoomObject> rooms = new ArrayList<>();
                for (int j = 0; j < arrRooms.length(); j++) {
                    JSONObject ojRoom = arrRooms.getJSONObject(j);

                    String roomID       = ojRoom.getString("ID");
                    String roomName     = ojRoom.getString("Name");
                    String roomType     = ojRoom.getString("Type");
                    String roomIconPic  = ojRoom.getString("IconPicture");
                    String roomWallPic  = ojRoom.getString("WallPicture");

                    JSONArray arrDevices = new JSONArray(ojRoom.getString("Devices"));
                    ArrayList<DeviceObject> devices = new ArrayList<>();
                    for (int k = 0; k < arrDevices.length(); k++) {
                        JSONObject ojDevices = arrDevices.getJSONObject(k);

                        String deviceID         = ojDevices.getString("ID");
                        String deviceName       = ojDevices.getString("Name");
                        String deviceDescr      = ojDevices.getString("Descr");
                        String deviceType       = ojDevices.getString("Type");
                        String deviceIconOn     = ojDevices.getString("IconOn");
                        String deviceIconOff    = ojDevices.getString("IconOff");

                        JSONArray arrPoints = new JSONArray(ojDevices.getString("Points"));
                        ArrayList<PointObject> points = new ArrayList<>();
                        for (int h = 0; h < arrPoints.length(); h++) {
                            JSONObject ojPoints = arrPoints.getJSONObject(h);

                            String pointID      = ojPoints.getString("ID");
                            String pointName    = ojPoints.getString("Name");
                            String pointDescr   = ojPoints.getString("Descr");
                            String pointType    = ojPoints.getString("Type");
                            String pointAddress = ojPoints.getString("Address");

                            points.add(new PointObject(pointID, pointName, pointDescr, pointType, pointAddress));
                        }

                        devices.add(new DeviceObject(deviceID, deviceName, deviceDescr, deviceType, deviceIconOn, deviceIconOff, true, points));
                    }

                    rooms.add(new RoomObject(roomID, roomName, roomType, roomIconPic, roomWallPic, devices));

                }
                floors.add(new FloorObject(floorID,floorName,floorDescr,rooms));


            }
            return floors;

        } catch (JSONException ex) {
            Log.e("manh", ex.toString());
            return null;
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            if (!mFragmentList.isEmpty())
                return mFragmentList.get(position);
            else {
                Log.e("manh","null");
                return null;
            }

        }

        @Override
        public int getCount() {
                return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }


}
