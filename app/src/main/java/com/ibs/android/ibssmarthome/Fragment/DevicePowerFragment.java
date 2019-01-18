package com.ibs.android.ibssmarthome.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibs.android.ibssmarthome.Comm;
import com.ibs.android.ibssmarthome.R;
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;

public class DevicePowerFragment extends Fragment {
    private TextView tvDevicePw;
    private ToggleSwitch tsDevicePw;
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
        tsDevicePw = view.findViewById(R.id.toggleswitch_devicepower);

        tsDevicePw.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {
                int value=0;
                if (i==0){
                    tvDevicePw.setText("Power ON");
                    value=1;
                }
                else if (i==1){
                    tvDevicePw.setText("Power OFF");
                    value=0;
                }
                SendToServer(value);
            }
        });
        return view;
    }

    private void SendToServer(int value){

    }
}
