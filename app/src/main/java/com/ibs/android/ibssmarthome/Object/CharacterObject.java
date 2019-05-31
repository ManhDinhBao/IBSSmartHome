package com.ibs.android.ibssmarthome.Object;

public class CharacterObject {
    private String ID;
    private String Data;

    public CharacterObject(String ID, String data) {
        this.ID = ID;
        Data = data;
    }

    public CharacterObject() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }
}
