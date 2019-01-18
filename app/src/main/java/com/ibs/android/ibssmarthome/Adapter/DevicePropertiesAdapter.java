package com.ibs.android.ibssmarthome.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ibs.android.ibssmarthome.Fragment.DevicePowerFragment;
import com.ibs.android.ibssmarthome.Fragment.DeviceValueFragment;

import java.util.ArrayList;

public class DevicePropertiesAdapter extends FragmentPagerAdapter {

    private ArrayList<Integer> deviceProperties;
    public DevicePropertiesAdapter(FragmentManager fragmentManager, ArrayList<Integer> deviceProperties) {
        super(fragmentManager);
        this.deviceProperties=deviceProperties;
    }

    @Override
    public Fragment getItem(int i) {
        switch (deviceProperties.get(i)){
            case 1:
                return new DevicePowerFragment();
            case 2:
                return new DeviceValueFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return deviceProperties.size();
    }
}
