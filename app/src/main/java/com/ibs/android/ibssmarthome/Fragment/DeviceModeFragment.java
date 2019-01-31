package com.ibs.android.ibssmarthome.Fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ibs.android.ibscontrol.ToggleSwitch.ToggleSwitch;
import com.ibs.android.ibssmarthome.Comm;
import com.ibs.android.ibssmarthome.R;

public class DeviceModeFragment extends Fragment {
    private String deviceType;
    private ToggleSwitch tsDeviceMode;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_devicemode, container, false);
        deviceType = "DT00000002";
        tsDeviceMode = view.findViewById(R.id.toggleswitch_deviceproperties_mode);

        //Get screen size
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        tsDeviceMode.setToggleWidth((float) (width * 0.3));
        tsDeviceMode.setToggleHeight((float) (height * 0.12));
        tsDeviceMode.setCornerRadius(48);
        tsDeviceMode.setTextSize(48);

        switch (deviceType) {
            case Comm.DEVICE_TYPE_AC:
                tsDeviceMode.setLabels(Comm.LIST_THERMOSTAT_MODE);
                break;
            case Comm.DEVICE_TYPE_WASHING_MACHINE:
                tsDeviceMode.setLabels(Comm.LIST_WASHINGMACHINE_MODE);
                break;
            case Comm.DEVICE_TYPE_FAN:
                tsDeviceMode.setLabels(Comm.LIST_FAN_MODE);
                break;
            default:
                tsDeviceMode.setLabels(Comm.LIST_FAN_MODE);
        }

        return view;
    }

}
