package com.ibs.android.ibssmarthome;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;

import com.ibs.android.ibssmarthome.Activity.RoomActivity;
import com.ibs.android.ibssmarthome.Adapter.DeviceAdapter;
import com.ibs.android.ibssmarthome.Object.DeviceObject;
import com.ibs.android.ibssmarthome.Object.HomeObject;
import com.ibs.android.ibssmarthome.Object.PointObject;
import com.ibs.android.ibssmarthome.Object.RoomObject;

import androidx.appcompat.app.AlertDialog;

public class Global {
    public static HomeObject sHome=null;
    public static boolean UpdatePointValueByTopic(String topic,String value){
        for (RoomObject room: sHome.getRooms()) {
            for (DeviceObject device:room.getDevices()) {
                for (PointObject point:device.getPoints()) {
                    if (point.getCmdAddress().matches(topic)){
                        switch (point.getType().getID()){
                            case Comm.POINT_TYPE_BOOL:
                                if (value.matches("0")){
                                    point.setValue(false);
                                    device.setPower(false);
                                }
                                else {
                                    point.setValue(true);
                                    device.setPower(true);
                                }
                                break;
                            case Comm.POINT_TYPE_NUMBER:
                                point.setValue(Float.parseFloat(value));
                                break;
                            case Comm.POINT_TYPE_TEXT:
                                point.setValue(value);
                                break;
                        }
                        if (RoomActivity.deviceAdapter!=null){
                            RoomActivity.deviceAdapter.notifyDataSetChanged();
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static RoomObject GetRoomById(String roomId){
        for (RoomObject room:sHome.getRooms()) {
            if (room.getID().matches(roomId)){
                return room;
            }
        }
        return null;
    }

    public static DeviceObject GetDeviceById(String deviceId){
        for (RoomObject room:sHome.getRooms()) {
            for (DeviceObject device: room.getDevices()) {
                if (device.getId().matches(deviceId)){
                    return device;
                }
            }
        }
        return null;
    }
    public static PointObject  GetPointById(String pointId){
        for (RoomObject room:sHome.getRooms()) {
            for (DeviceObject device: room.getDevices()) {
                for (PointObject pointObject :device.getPoints()){
                    if (pointObject.getId().matches(pointId)){
                        return pointObject;
                    }
                }

            }
        }
        return null;
    }

    public static DeviceAdapter deviceAdapter;
    public static String apartmentId=null;

    public static void ShowDialog(String title, String content, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
