package com.ibs.android.ibssmarthome.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ibs.android.ibssmarthome.Comm;
import com.ibs.android.ibssmarthome.Fragment.DeviceMultiValueFragment;
import com.ibs.android.ibssmarthome.Fragment.DevicePowerFragment;
import com.ibs.android.ibssmarthome.Fragment.DeviceRangeFragment;
import com.ibs.android.ibssmarthome.Object.PointCharacterObject;

import java.util.ArrayList;

public class DevicePropertiesAdapter extends FragmentPagerAdapter {

    private ArrayList<PointCharacterObject> pointCharacter;
    public DevicePropertiesAdapter(FragmentManager fragmentManager, ArrayList<PointCharacterObject> pointCharacter) {
        super(fragmentManager);
        this.pointCharacter =pointCharacter;
    }

    @Override
    public Fragment getItem(int i) {
        switch (pointCharacter.get(i).getType()){
            case Comm.POINT_CHARACTER_POWER:
                return DevicePowerFragment.newInstance(pointCharacter.get(i).getValue(),pointCharacter.get(i).getPoint().getId());
            case Comm.POINT_CHARACTER_RANGE:
                return DeviceRangeFragment.newInstance(pointCharacter.get(i).getValue(),pointCharacter.get(i).getPoint().getId());
            case Comm.POINT_CHARACTER_MULTIVALUE:
                return DeviceMultiValueFragment.newInstance(pointCharacter.get(i).getValue(),pointCharacter.get(i).getPoint().getId());
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pointCharacter.size();
    }
}
