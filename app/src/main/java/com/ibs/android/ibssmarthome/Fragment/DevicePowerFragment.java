package com.ibs.android.ibssmarthome.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ibs.android.ibscontrol.ToggleSwitch.BaseToggleSwitch;
import com.ibs.android.ibscontrol.ToggleSwitch.ToggleSwitch;
import com.ibs.android.ibssmarthome.Activity.MainActivity;
import com.ibs.android.ibssmarthome.Comm;
import com.ibs.android.ibssmarthome.Global;
import com.ibs.android.ibssmarthome.Object.PointObject;
import com.ibs.android.ibssmarthome.Object.TCPMessageObject;
import com.ibs.android.ibssmarthome.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class DevicePowerFragment extends Fragment {
    private TextView tvDevicePw;
    private ToggleSwitch tsDevicePower;
    private String PowerData,pointID;
    private ArrayList<String> labels;
    private ArrayList<Integer> values;
    private PointObject pointObject;

    public static DevicePowerFragment newInstance(String Data,String pointID) {
        DevicePowerFragment myFragment = new DevicePowerFragment();

        Bundle args = new Bundle();
        args.putString("data", Data);
        args.putString("pointID", pointID);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PowerData = getArguments().getString("data");
        pointID = getArguments().getString("pointID");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_devicepower, container, false);
        labels=new ArrayList<>();
        values=new ArrayList<>();
        //Get data from json
        try
        {
            JSONArray array = new JSONArray(PowerData);
            for (int i=0;i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
                Iterator<String> iter = object.keys();
                while (iter.hasNext()){
                    String key = iter.next();
                    labels.add(key);

                    int value = object.getInt(key);
                    values.add(value);
                }
            }
        }
        catch (Exception ex){

        }

        //Get point
        pointObject = Global.GetPointById(pointID);


        //region Control Define
        tvDevicePw = view.findViewById(R.id.textview_devicepower_title);
        tsDevicePower = view.findViewById(R.id.toggleswitch_deviceproperties_power);
        //endregion

        //region Control Set
        //Get toggle tsDevicePower width and height from resource
        int width=getResources().getInteger(R.integer.toggle_power_width);
        int height=getResources().getInteger(R.integer.toggle_power_height);

        //Set toggle tsDevicePower size
        tsDevicePower.setToggleWidth(width);
        tsDevicePower.setToggleHeight(height);
        tsDevicePower.setCornerRadius(100);
        tsDevicePower.setTextSize(36);
        tsDevicePower.setLabels(labels);
        //endregion

        //region Control Event
        tsDevicePower.setOnToggleSwitchChangeListener(new BaseToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                int value = values.get(position);
                ArrayList<String> params = new ArrayList<>();
                params.add(pointObject.getCmdAddress());
                params.add(Integer.toString(value));
                TCPMessageObject message = new TCPMessageObject(Comm.MESSAGE_CODE_SEND_POINT,params);
                MainActivity.mTcpClient.SendMessage(message.toString());
            }
        });
        //endregion

        return view;
    }

}
