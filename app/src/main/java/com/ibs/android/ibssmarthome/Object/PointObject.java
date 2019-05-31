package com.ibs.android.ibssmarthome.Object;


public class PointObject {
    private String Id;
    private String Name;
    private String Description;
    private String CmdAddress; //MQTT command address
    private String SttAddress; //MQTT status address
    private PointTypeObject Type;
    private CharacterObject Character;
    private Object Value;

    public PointObject()  {
    }

    public PointObject(String id, String name, String description, String cmdAddress, String sttAddress, PointTypeObject type, CharacterObject character) {
        Id = id;
        Name = name;
        Description = description;
        CmdAddress = cmdAddress;
        SttAddress = sttAddress;
        Type = type;
        Character = character;
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

    public String getCmdAddress() {
        return CmdAddress;
    }

    public void setCmdAddress(String cmdAddress) {
        CmdAddress = cmdAddress;
    }

    public String getSttAddress() {
        return SttAddress;
    }

    public void setSttAddress(String sttAddress) {
        SttAddress = sttAddress;
    }

    public PointTypeObject getType() {
        return Type;
    }

    public void setType(PointTypeObject type) {
        Type = type;
    }

    public Object getValue() {
        return Value;
    }
    public void setValue(Object value) {
        Value = value;
    }

    public CharacterObject getCharacter() {
        return Character;
    }

    public void setCharacter(CharacterObject character) {
        Character = character;
    }

}
