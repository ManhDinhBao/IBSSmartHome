package com.ibs.android.ibssmarthome;

import android.content.Context;
import android.content.DialogInterface;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;

public class Comm {
    public static final String strAPI = "http://192.168.1.51/RestScadaAPI/api/";
    public static final String POINT_CHARACTER_POWER            = "BOOLEAN";
    public static final String POINT_CHARACTER_RANGE            = "RANGE";
    public static final String POINT_CHARACTER_MULTIVALUE       = "MULTIVALUE";

    public static final String POINT_TYPE_BOOL              = "PT00000001";
    public static final String POINT_TYPE_NUMBER            = "PT00000002";
    public static final String POINT_TYPE_TEXT              = "PT00000003";

    public static final String ROOM_TYPE_LIVING             = "RT00000001";
    public static final String ROOM_TYPE_BED                = "RT00000002";
    public static final String ROOM_TYPE_BATH               = "RT00000003";
    public static final String ROOM_TYPE_KITCHEN            = "RT00000004";
    public static final String ROOM_TYPE_BALCONY            = "RT00000005";
    public static final String ROOM_TYPE_YARD               = "RT00000006";

    public static final String MESSAGE_CODE_LOGIN           = "1201";
    public static final String MESSAGE_CODE_LOGIN_RSP       = "1202";
    public static final String MESSAGE_CODE_SEND_POINT      = "1203";
    public static final String MESSAGE_CODE_POINT_STATUS    = "1204";

    public static final String DEVICE_PROPERTIES_POWERON = "Power On";
    public static final String DEVICE_PROPERTIES_POWEROFF = "Power Off";

    public static final String DEVICE_TYPE_LIGHT                = "DT00000001";
    public static final String DEVICE_TYPE_AC                   = "DT00000002";
    public static final String DEVICE_TYPE_EW                   = "DT00000003";
    public static final String DEVICE_TYPE_DOOR                 = "DT00000007";
    public static final String DEVICE_TYPE_FAN                  = "DT00000008";
    public static final String DEVICE_TYPE_TV                   = "DT00000006";
    public static final String DEVICE_TYPE_WASHING_MACHINE      = "DT00000009";

    public static final String API_RESULT_OK = "OK";

    public static final String MQTT_SERVER_ADDRESS = "tcp://192.168.0.51:80063";

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
    public static void ShowDialog(String title, String content, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
