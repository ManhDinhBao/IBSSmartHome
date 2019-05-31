package com.ibs.android.ibssmarthome.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ibs.android.ibssmarthome.Activity.MainActivity;
import com.ibs.android.ibssmarthome.Comm;
import com.ibs.android.ibssmarthome.Global;
import com.ibs.android.ibssmarthome.Object.PointObject;
import com.ibs.android.ibssmarthome.Object.TCPMessageObject;
import com.ibs.android.ibssmarthome.R;

import org.json.JSONArray;

import java.util.ArrayList;

import abak.tr.com.boxedverticalseekbar.BoxedVertical;

public class DeviceRangeFragment extends Fragment {
    private BoxedVertical bvDeviceValue;
    private TextView tvDeviceValue;
    private String RangeData,pointID;
    private int minValue,maxValue;
    private PointObject pointObject;

    public static DeviceRangeFragment newInstance(String Data,String pointID) {
        DeviceRangeFragment myFragment = new DeviceRangeFragment();

        Bundle args = new Bundle();
        args.putString("data", Data);
        args.putString("pointID", pointID);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RangeData = getArguments().getString("data");
        pointID = getArguments().getString("pointID");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_devicevalue, container, false);
        tvDeviceValue = view.findViewById(R.id.textview_devicevalue);
        bvDeviceValue = view.findViewById(R.id.boxedvertical_devicevalue);

        //Get data
        try
        {
            JSONArray array = new JSONArray(RangeData);
            if (array.length()>0){
                maxValue = array.getJSONObject(1).getInt("MAX");
                minValue = array.getJSONObject(0).getInt("MIN");
            }
            else {
                minValue =0;
                maxValue=100;
            }
        }
        catch (Exception ex){
            minValue =0;
            maxValue=100;
        }

        //Get point
        pointObject = Global.GetPointById(pointID);

        bvDeviceValue.setMax(maxValue);
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
                ArrayList<String> params = new ArrayList<>();
                params.add(pointObject.getCmdAddress());
                params.add(Integer.toString(boxedVertical.getValue()));
                TCPMessageObject message = new TCPMessageObject(Comm.MESSAGE_CODE_SEND_POINT,params);
                MainActivity.mTcpClient.SendMessage(message.toString());
            }
        });
        return view;
    }
}
