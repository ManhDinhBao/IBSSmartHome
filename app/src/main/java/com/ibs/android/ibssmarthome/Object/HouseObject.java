package com.ibs.android.ibssmarthome.Object;

import java.util.ArrayList;

public class HouseObject {
    private String Id;
    private String Name;
    private ArrayList<FloorObject> floors;

    public HouseObject() {
    }

    public HouseObject(String id, String name, ArrayList<FloorObject> floors) {
        Id = id;
        Name = name;
        this.floors = floors;
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

    public ArrayList<FloorObject> getFloors() {
        return floors;
    }

    public void setFloors(ArrayList<FloorObject> floors) {
        this.floors = floors;
    }
}
