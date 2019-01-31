package com.ibs.android.ibssmarthome;

import java.util.ArrayList;

public class Comm {
    public static final String strAPI = "http://192.168.1.51/RestScadaAPI/api/";
    public static final String DEVICE_TYPE_LIGHT            = "DT00000001";
    public static final String DEVICE_TYPE_AC               = "DT00000002";
    public static final String DEVICE_TYPE_EW               = "DT00000003";
    public static final String DEVICE_TYPE_BUTTON           = "DT00000004";
    public static final String DEVICE_TYPE_METER            = "DT00000005";
    public static final String DEVICE_TYPE_TV               = "DT00000006";
    public static final String DEVICE_TYPE_DOOR             = "DT00000007";
    public static final String DEVICE_TYPE_FAN              = "DT00000008";
    public static final String DEVICE_TYPE_WASHING_MACHINE  = "DT00000009";

    public static final String DEVICE_PROPERTIES_POWERON = "Power On";
    public static final String DEVICE_PROPERTIES_POWEROFF = "Power Off";

    public static final int DEVICE_PROPERTIES_POWER = 1;
    public static final int DEVICE_PROPERTIES_VALUE = 2;
    public static final int DEVICE_PROPERTIES_MODE = 3;

    public static final String API_RESULT_OK = "OK";

    public static final ArrayList<String> LIST_THERMOSTAT_MODE = new ArrayList<String>(){
        {
            add("FAN");
            add("DRY");
            add("COOL");
            add("AUTO");
            add("HEAT");
        }
    };

    public static final ArrayList<String> LIST_WASHINGMACHINE_MODE = new ArrayList<String>(){
        {
            add("WASH");
            add("SPIN");
            add("DRY");
        }
    };

    public static final ArrayList<String> LIST_FAN_MODE = new ArrayList<String>(){
        {
            add("LOW");
            add("MEDIUM");
            add("HIGH");
        }
    };
}
