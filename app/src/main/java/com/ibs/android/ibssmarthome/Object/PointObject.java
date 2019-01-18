package com.ibs.android.ibssmarthome.Object;

public class PointObject {
    private String Id;
    private String Name;
    private String Description;
    private String Address; //MQTT address
    private String Type;

    //Type PT00000001: ON/OFF
    //     PT00000002: BRIGHTNESS
    //     PT00000003: SPEED
    //     PT00000004: MODE
    //     PT00000005: TEMPERATURE
    //     PT00000006: SWING
    //     PT00000007: SHOW TEXT


    public PointObject() {
    }

    public PointObject(String id, String name, String description, String address, String type) {
        Id = id;
        Name = name;
        Description = description;
        Address = address;
        Type = type;
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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

}
