package com.ibs.android.ibssmarthome.Object;

public class RoomTypeObject {
    private String ID;
    private String Name;

    public RoomTypeObject(String ID, String name) {
        this.ID = ID;
        Name = name;
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
}
