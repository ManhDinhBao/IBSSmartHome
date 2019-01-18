package com.ibs.android.ibssmarthome.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ibs.android.ibssmarthome.R;

import abak.tr.com.boxedverticalseekbar.BoxedVertical;

public class DeviceValueFragment extends Fragment {
    private BoxedVertical bvDeviceValue;
    private TextView tvDeviceValue;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_devicevalue, container, false);
        tvDeviceValue = view.findViewById(R.id.textview_devicevalue);
        bvDeviceValue = view.findViewById(R.id.boxedvertical_devicevalue);

        bvDeviceValue.setOnBoxedPointsChangeListener(new BoxedVertical.OnValuesChangeListener() {
            @Override
            public void onPointsChanged(BoxedVertical boxedVertical, int i) {
                tvDeviceValue.setText(Integer.toString(i));
            }

            @Override
            public void onStartTrackingTouch(BoxedVertical boxedVertical) {

            }

            @Override
            public void onStopTrackingTouch(BoxedVertical boxedVertical) {
                SendToServer(boxedVertical.getValue());
            }
        });
        return view;
    }

    private void SendToServer(int value){

    }
}
