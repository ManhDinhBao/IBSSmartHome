package com.ibs.android.ibssmarthome.Object;

import java.util.ArrayList;

public class DeviceObject {
    private String Id;
    private String Name;
    private String Description;
    private String Type;
    private String IconOn;
    private String IconOff;
    private boolean Power;
    private ArrayList<PointObject> points;

    //Type: DT00000001:LIGHT
    // DT00000002:THERMOSTAT
    // DT00000003:HEATER
    // DT00000004:BUTTON
    // DT00000005:METER

    public DeviceObject() {
    }

    public DeviceObject(String id, String name, String description, String type, String iconOn, String iconOff, boolean power, ArrayList<PointObject> points) {
        Id = id;
        Name = name;
        Description = description;
        Type = type;
        IconOn = iconOn;
        IconOff = iconOff;
        Power = power;
        this.points = points;
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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getIconOn() {
        return IconOn;
    }

    public void setIconOn(String iconOn) {
        IconOn = iconOn;
    }

    public String getIconOff() {
        return IconOff;
    }

    public void setIconOff(String iconOff) {
        IconOff = iconOff;
    }

    public boolean isPower() {
        return Power;
    }

    public void setPower(boolean power) {
        Power = power;
    }

    public ArrayList<PointObject> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<PointObject> points) {
        this.points = points;
    }
}
