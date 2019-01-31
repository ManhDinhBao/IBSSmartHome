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
import android.widget.TextView;

import com.ibs.android.ibscontrol.ToggleSwitch.BaseToggleSwitch;
import com.ibs.android.ibscontrol.ToggleSwitch.ToggleSwitch;
import com.ibs.android.ibssmarthome.Comm;
import com.ibs.android.ibssmarthome.R;

import java.util.ArrayList;

public class DevicePowerFragment extends Fragment {
    private TextView tvDevicePw;
    private ToggleSwitch tsDevicePower;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_devicepower, container, false);
        tvDevicePw = view.findViewById(R.id.textview_devicepower_title);
        tsDevicePower = view.findViewById(R.id.toggleswitch_deviceproperties_power);

        //Get screen size
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //Set toggle size
        tsDevicePower.setToggleWidth((float) (width * 0.3));
        tsDevicePower.setToggleHeight((float) (height * 0.3));
        tsDevicePower.setCornerRadius(48);

        //Set toggle child
        ArrayList<String> labels = new ArrayList<>();
        labels.add(" ");
        labels.add(" ");
        tsDevicePower.setLabels(labels);

        tsDevicePower.setOnToggleSwitchChangeListener(new BaseToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                switch (position) {
                    case 0:
                        tvDevicePw.setText(Comm.DEVICE_PROPERTIES_POWERON);
                        break;
                    case 1:
                        tvDevicePw.setText(Comm.DEVICE_PROPERTIES_POWEROFF);
                        break;
                    default:
                        tvDevicePw.setText(Comm.DEVICE_PROPERTIES_POWEROFF);
                }
            }
        });

        return view;
    }

    private void SendToServer(int value) {

    }
}
