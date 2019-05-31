package com.ibs.android.ibssmarthome.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class DeviceMultiValueFragment extends Fragment {
    private ToggleSwitch tsDeviceMode;
    private String ValueData,pointID;
    private ArrayList<String> labels;
    private ArrayList<Integer> values;
    private PointObject pointObject;

    public static DeviceMultiValueFragment newInstance(String Data,String pointID) {
        DeviceMultiValueFragment myFragment = new DeviceMultiValueFragment();

        Bundle args = new Bundle();
        args.putString("data", Data);
        args.putString("pointID", pointID);
        myFragment.setArguments(args);

        return myFragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ValueData = getArguments().getString("data");
        pointID = getArguments().getString("pointID");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_devicemode, container, false);
        labels=new ArrayList<>();
        values=new ArrayList<>();

        try
        {
            JSONArray array = new JSONArray(ValueData);
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
        tsDeviceMode = view.findViewById(R.id.toggleswitch_deviceproperties_mode);

        //endregion

        //region Control Set
        //Get toggle tsDeviceMode width and height from resource
        int width=getResources().getInteger(R.integer.toggle_power_width);
        int height=getResources().getInteger(R.integer.toggle_power_height);

        //Set toggle tsDeviceMode size
        tsDeviceMode.setToggleWidth(width);
        tsDeviceMode.setToggleHeight(height-100);
        tsDeviceMode.setCornerRadius(100);
        tsDeviceMode.setTextSize(36);
        tsDeviceMode.setLabels(labels);
        //endregion

        //region Control Event
        tsDeviceMode.setOnToggleSwitchChangeListener(new BaseToggleSwitch.OnToggleSwitchChangeListener() {
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
