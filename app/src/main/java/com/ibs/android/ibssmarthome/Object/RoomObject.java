package com.ibs.android.ibssmarthome.Object;

import java.util.ArrayList;

public class RoomObject {
    private String ID;
    private String Name;
    private RoomTypeObject Type;
    private String IconPicture;
    private String WallPicture;
    private ArrayList<DeviceObject> devices;

    public RoomObject() {
    }

    public RoomObject(String ID, String name, RoomTypeObject type, String iconPicture, String wallPicture, ArrayList<DeviceObject> devices) {
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

    public RoomTypeObject getType() {
        return Type;
    }

    public void setType(RoomTypeObject type) {
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
