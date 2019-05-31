package com.ibs.android.ibssmarthome.Object;

public class PointCharacterObject {
    private PointObject Point;
    private String Type;
    private String Value;

    public PointCharacterObject(PointObject point, String type, String value) {
        Point = point;
        Type = type;
        Value = value;
    }

    public PointObject getPoint() {
        return Point;
    }

    public void setPoint(PointObject point) {
        Point = point;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}