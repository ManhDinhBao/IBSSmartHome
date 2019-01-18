package com.ibs.android.ibssmarthome.Object;

import java.util.ArrayList;

public class RoomObject {
    private String ID;
    private String Name;
    private String Type;
    private String IconPicture;
    private String WallPicture;
    private ArrayList<DeviceObject> devices;

    //TYPE: 1:LIVING 2:BED 3:BATH 4:KITCHEN 5:BALCONY

    public RoomObject() {
    }

    public RoomObject(String ID, String name, String type, String iconPicture, String wallPicture, ArrayList<DeviceObject> devices) {
        this.ID = ID;
        Name = name;
        Type = type;
        IconPicture = iconPicture;
        WallPicture = wallPicture;
        this.devices = devices;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getIconPicture() {
        return IconPicture;
    }

    public void setIconPicture(String iconPicture) {
        IconPicture = iconPicture;
    }

    public String getWallPicture() {
        return WallPicture;
    }

    public void setWallPicture(String wallPicture) {
        WallPicture = wallPicture;
    }

    public ArrayList<DeviceObject> getDevices() {
        return devices;
    }

    public void setDevices(ArrayList<DeviceObject> devices) {
        this.devices = devices;
    }

}
