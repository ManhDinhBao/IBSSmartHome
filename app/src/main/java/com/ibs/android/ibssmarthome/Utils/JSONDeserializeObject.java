package com.ibs.android.ibssmarthome.Utils;

import android.util.Log;

import com.ibs.android.ibssmarthome.Object.CharacterObject;
import com.ibs.android.ibssmarthome.Object.DeviceObject;
import com.ibs.android.ibssmarthome.Object.DeviceTypeObject;
import com.ibs.android.ibssmarthome.Object.HomeObject;
import com.ibs.android.ibssmarthome.Object.PointObject;
import com.ibs.android.ibssmarthome.Object.PointTypeObject;
import com.ibs.android.ibssmarthome.Object.RoomObject;
import com.ibs.android.ibssmarthome.Object.RoomTypeObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONDeserializeObject {
    public static HomeObject JSONToHomeObject(String jsonData){
        try {
            JSONObject objHouse = new JSONObject(jsonData);
            if (objHouse!=null){
                String ID               = objHouse.getString("ID");
                String Name             = objHouse.getString("Name");
                String Description      = objHouse.getString("Description");

                JSONArray arrRooms = new JSONArray(objHouse.getString("ListRooms"));
                ArrayList<RoomObject> rooms = new ArrayList<>();
                for (int j = 0; j < arrRooms.length(); j++) {
                    JSONObject objRoom = arrRooms.getJSONObject(j);

                    String roomID       = objRoom.getString("ID");
                    String roomName     = objRoom.getString("Name");
                    String roomIconPic  = objRoom.getString("IconPicture");
                    String roomWallPic  = objRoom.getString("WallPicture");

                    JSONObject roomType = objRoom.getJSONObject("Type");
                    String roomTypeId   = roomType.getString("ID");
                    String roomTypeName = roomType.getString("Name");

                    RoomTypeObject roomTypeObject = new RoomTypeObject(roomTypeId,roomTypeName);

                    JSONArray arrDevices = new JSONArray(objRoom.getString("Devices"));
                    ArrayList<DeviceObject> devices = new ArrayList<>();
                    for (int k = 0; k < arrDevices.length(); k++) {
                        JSONObject objDevices = arrDevices.getJSONObject(k);

                        String deviceID         = objDevices.getString("ID");
                        String deviceName       = objDevices.getString("Name");
                        String deviceDescr      = objDevices.getString("Descr");
                        String deviceIconOn     = objDevices.getString("IconOn");
                        String deviceIconOff    = objDevices.getString("IconOff");

                        JSONObject deviceType   = objDevices.getJSONObject("Type");
                        String deviceTypeId     = deviceType.getString("ID");
                        String deviceTypeName   = deviceType.getString("Name");

                        DeviceTypeObject deviceTypeObject = new DeviceTypeObject(deviceTypeId,deviceTypeName);

                        JSONArray arrPoints     = new JSONArray(objDevices.getString("Points"));
                        ArrayList<PointObject> points = new ArrayList<>();
                        for (int h = 0; h < arrPoints.length(); h++) {
                            JSONObject objPoints = arrPoints.getJSONObject(h);

                            String pointID      = objPoints.getString("ID");
                            String pointName    = objPoints.getString("Name");
                            String pointDescr   = objPoints.getString("Descr");
                            String subAddress   = objPoints.getString("SubcribeAddress");
                            String pubAddress   = objPoints.getString("PublishAddress");

                            JSONObject pointType       = objPoints.getJSONObject("Type");
                            String pointTypeTypeId     = pointType.getString("ID");
                            String pointTypeTypeName   = pointType.getString("Name");
                            PointTypeObject pointTypeObject=new PointTypeObject(pointTypeTypeId,pointTypeTypeName);


                            JSONObject character       = objPoints.getJSONObject("Character");
                            String characterId         = character.getString("ID");
                            String characterData       = character.getString("Data");
                            CharacterObject characterObject = new CharacterObject(characterId,characterData);


                            points.add(new PointObject(pointID,pointName,pointDescr,pubAddress,subAddress,pointTypeObject,characterObject));
                        }

                        devices.add(new DeviceObject(deviceID,deviceName,deviceDescr,deviceTypeObject,deviceIconOn,deviceIconOff,false,points));
                    }

                    rooms.add(new RoomObject(roomID,roomName,roomTypeObject,roomIconPic,roomWallPic,devices));
                }
                HomeObject homeObject =new HomeObject(ID,Name,Description,rooms);
                return homeObject;
            }
            return null;
        } catch (JSONException ex) {
            Log.e("manh", ex.toString());
            return null;
        }
    }
    public static DeviceObject JSONToDeviceObject(String jsonData) {
        try {
            JSONObject objDevice = new JSONObject(jsonData);

            String deviceId         = objDevice.getString("ID");
            String deviceName       = objDevice.getString("Name");
            String deviceDescr      = objDevice.getString("Descr");
            String deviceIconOn     = objDevice.getString("IconOn");
            String deviceIconOff    = objDevice.getString("IconOff");

            JSONObject deviceType   = objDevice.getJSONObject("Type");
            String deviceTypeId     = deviceType.getString("ID");
            String deviceTypeName   = deviceType.getString("Name");

            DeviceTypeObject deviceTypeObject=new DeviceTypeObject(deviceTypeId,deviceTypeName);

            JSONArray arrPoints     = new JSONArray(objDevice.getString("Points"));
            ArrayList<PointObject> points = new ArrayList<>();
            for (int h = 0; h < arrPoints.length(); h++) {
                JSONObject objPoints = arrPoints.getJSONObject(h);

                String pointID      = objPoints.getString("ID");
                String pointName    = objPoints.getString("Name");
                String pointDescr   = objPoints.getString("Descr");
                String subAddress   = objPoints.getString("SubcribeAddress");
                String pubAddress   = objPoints.getString("PublishAddress");

                JSONObject pointType       = objPoints.getJSONObject("Type");
                String pointTypeTypeId     = pointType.getString("ID");
                String pointTypeTypeName   = pointType.getString("Name");
                PointTypeObject pointTypeObject=new PointTypeObject(pointTypeTypeId,pointTypeTypeName);


                JSONObject character       = objPoints.getJSONObject("Character ");
                String characterId         = pointType.getString("ID");
                String characterData       = pointType.getString("Data");
                CharacterObject characterObject = new CharacterObject(characterId,characterData);


                points.add(new PointObject(pointID,pointName,pointDescr,pubAddress,subAddress,pointTypeObject,characterObject));
            }

            return new DeviceObject(deviceId,deviceName,deviceDescr,deviceTypeObject,deviceIconOn,deviceIconOff,false,points);

        } catch (JSONException ex) {
            Log.e("manh", ex.toString());
            return null;
        }
    }
    public static RoomObject JSONToRoomObject(String jsonData) {
        try {
            JSONObject objRoom = new JSONObject(jsonData);

            String roomID = objRoom.getString("ID");
            String roomName = objRoom.getString("Name");
            String roomIconPic = objRoom.getString("IconPicture");
            String roomWallPic = objRoom.getString("WallPicture");

            JSONObject roomType = objRoom.getJSONObject("Type");
            String roomTypeId = roomType.getString("ID");
            String roomTypeName = roomType.getString("Name");

            RoomTypeObject roomTypeObject = new RoomTypeObject(roomTypeId, roomTypeName);

            JSONArray arrDevices = new JSONArray(objRoom.getString("Devices"));
            ArrayList<DeviceObject> devices = new ArrayList<>();
            for (int k = 0; k < arrDevices.length(); k++) {
                JSONObject objDevices = arrDevices.getJSONObject(k);

                String deviceID = objDevices.getString("ID");
                String deviceName = objDevices.getString("Name");
                String deviceDescr = objDevices.getString("Descr");
                String deviceIconOn = objDevices.getString("IconOn");
                String deviceIconOff = objDevices.getString("IconOff");

                JSONObject deviceType = objDevices.getJSONObject("Type");
                String deviceTypeId = deviceType.getString("ID");
                String deviceTypeName = deviceType.getString("Name");

                DeviceTypeObject deviceTypeObject = new DeviceTypeObject(deviceTypeId, deviceTypeName);

                JSONArray arrPoints = new JSONArray(objDevices.getString("Points"));
                ArrayList<PointObject> points = new ArrayList<>();
                for (int h = 0; h < arrPoints.length(); h++) {
                    JSONObject objPoints = arrPoints.getJSONObject(h);

                    String pointID      = objPoints.getString("ID");
                    String pointName    = objPoints.getString("Name");
                    String pointDescr   = objPoints.getString("Descr");
                    String subAddress   = objPoints.getString("SubcribeAddress");
                    String pubAddress   = objPoints.getString("PublishAddress");

                    JSONObject pointType       = objPoints.getJSONObject("Type");
                    String pointTypeTypeId     = pointType.getString("ID");
                    String pointTypeTypeName   = pointType.getString("Name");
                    PointTypeObject pointTypeObject=new PointTypeObject(pointTypeTypeId,pointTypeTypeName);


                    JSONObject character       = objPoints.getJSONObject("Character ");
                    String characterId         = pointType.getString("ID");
                    String characterData       = pointType.getString("Data");
                    CharacterObject characterObject = new CharacterObject(characterId,characterData);


                    points.add(new PointObject(pointID,pointName,pointDescr,pubAddress,subAddress,pointTypeObject,characterObject));
                }

                devices.add(new DeviceObject(deviceID, deviceName, deviceDescr, deviceTypeObject, deviceIconOn, deviceIconOff, false, points));

            }
            return new RoomObject(roomID,roomName,roomTypeObject,roomIconPic,roomWallPic,devices);

        } catch (JSONException ex) {
            Log.e("manh", ex.toString());
            return null;
        }
    }
}
