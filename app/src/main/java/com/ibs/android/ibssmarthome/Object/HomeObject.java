package com.ibs.android.ibssmarthome.Object;

import java.util.ArrayList;

public class HomeObject {
    private String Id;
    private String Name;
    private String Description;
    private ArrayList<RoomObject> rooms;

    public HomeObject() {
    }

    public HomeObject(String id, String name, String description, ArrayList<RoomObject> rooms) {
        Id = id;
        Name = name;
        Description = description;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public ArrayList<RoomObject> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<RoomObject> rooms) {
        this.rooms = rooms;
    }
}
