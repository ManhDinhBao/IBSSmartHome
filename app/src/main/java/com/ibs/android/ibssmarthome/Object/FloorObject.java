package com.ibs.android.ibssmarthome.Object;

import java.util.ArrayList;

public class FloorObject {
    private String Id;
    private String Name;
    private String Descr;
    private ArrayList<RoomObject> rooms;

    public FloorObject() {
    }

    public FloorObject(String id, String name, String descr, ArrayList<RoomObject> rooms) {
        Id = id;
        Name = name;
        Descr = descr;
        this.rooms = rooms;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescr() {
        return Descr;
    }

    public void setDescr(String descr) {
        Descr = descr;
    }

    public ArrayList<RoomObject> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<RoomObject> rooms) {
        this.rooms = rooms;
    }
}
