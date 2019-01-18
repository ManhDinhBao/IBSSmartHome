package com.ibs.android.ibssmarthome.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.card.MaterialCardView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ibs.android.ibssmarthome.Adapter.DeviceModeAdapter;
import com.ibs.android.ibssmarthome.Comm;
import com.ibs.android.ibssmarthome.R;

import java.util.ArrayList;

public class DeviceModeFragment extends Fragment {
    private GridView gridDeviceMode;
    private ArrayList<String> lstDeviceMode;
    private String deviceType;
    private DeviceModeAdapter deviceModeAdapter;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_devicemode, container, false);
        gridDeviceMode = view.findViewById(R.id.gridDevice);
        loadDeviceMode(deviceType);
        deviceModeAdapter = new DeviceModeAdapter(getActivity().getApplicationContext(), lstDeviceMode);
        gridDeviceMode.setAdapter(deviceModeAdapter);

        gridDeviceMode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(100);

                MaterialCardView materialCardView = (MaterialCardView)view;

            }
        });
        return view;
    }

    private void loadDeviceMode(String deviceType){
        lstDeviceMode = new ArrayList<>();
        switch (deviceType){
            case Comm.DEVICE_TYPE_AC:
                lstDeviceMode.add("FAN");
                lstDeviceMode.add("DRY");
                lstDeviceMode.add("COOL");
                lstDeviceMode.add("AUTO");
                lstDeviceMode.add("HEAT");
                break;
            case Comm.DEVICE_TYPE_WASHING_MACHINE:
                lstDeviceMode.add("WASH");
                lstDeviceMode.add("SPIN");
                lstDeviceMode.add("DRY");
                break;
            case Comm.DEVICE_TYPE_FAN:
                lstDeviceMode.add("LOW");
                lstDeviceMode.add("MEDIUM");
                lstDeviceMode.add("HIGH");
                break;
        }
    }
}
