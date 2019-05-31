package com.ibs.android.ibssmarthome.Object;

public class PointValueObject {
    private String Name;
    private int Value;

    public PointValueObject(String name, int value) {
        Name = name;
        Value = value;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }
}
