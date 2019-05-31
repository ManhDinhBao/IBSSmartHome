package com.ibs.android.ibssmarthome.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import jp.wasabeef.blurry.Blurry;

import android.util.Log;
import android.widget.ImageView;

import com.ibs.android.ibssmarthome.Adapter.DevicePropertiesAdapter;
import com.ibs.android.ibssmarthome.Comm;
import com.ibs.android.ibssmarthome.Global;
import com.ibs.android.ibssmarthome.Object.DeviceObject;
import com.ibs.android.ibssmarthome.Object.PointCharacterObject;
import com.ibs.android.ibssmarthome.Object.PointObject;
import com.ibs.android.ibssmarthome.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DevicePropertiesActivity extends AppCompatActivity {

    private ViewPager                   viewPagerDeviceProperties;
    private TabLayout                   tabDeviceProperties;
    private DevicePropertiesAdapter     adapterViewPager;
    private String                      deviceId = "",roomTypeId;
    private Bitmap                      bmBackGround;
    private ImageView                   mIvRoomBlurBackground;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deviceproperties);

        //Get room type and device id from Room activity
        deviceId     = getIntent().getStringExtra("EXTRA_DEVICE_ID");
        roomTypeId   = getIntent().getStringExtra("EXTRA_ROOM_TYPE_ID");

        //Get device info by id
        DeviceObject device = Global.GetDeviceById(deviceId);

        //Get Character of each point in device
        ArrayList<PointCharacterObject> pointCharacter = new ArrayList<>();
        try {
            for (PointObject p : device.getPoints()) {

                JSONObject objCharacter     = new JSONObject(p.getCharacter().getData());
                String charType             = objCharacter.getString("TYPE");
                Log.d("SmartHome","DevicePropertiesActivity: Type - "+charType);
                JSONArray arrValue          = objCharacter.getJSONArray("VALUE");
                Log.d("SmartHome","DevicePropertiesActivity: Value -"+arrValue.toString());
                pointCharacter.add(new PointCharacterObject(p,charType,arrValue.toString()));
            }

        } catch (Exception ex) {
            Log.e("manh", ex.toString());
        }

        //region Control Define
        viewPagerDeviceProperties = findViewById(R.id.device_properties_viewpager);
        tabDeviceProperties = findViewById(R.id.tab_layout_device_properties);
        mIvRoomBlurBackground = findViewById(R.id.imageView_deviceproperties_blurBackground);
        //endregion

        //region Control Set
        //Get background image based on room type
        switch (roomTypeId) {
            case Comm.ROOM_TYPE_LIVING:
                bmBackGround            = BitmapFactory.decodeResource(getResources(), R.drawable.living_room);
                break;
            case Comm.ROOM_TYPE_BED:
                bmBackGround            = BitmapFactory.decodeResource(getResources(), R.drawable.bedroom);
                break;
            case Comm.ROOM_TYPE_BATH:
                bmBackGround            = BitmapFactory.decodeResource(getResources(), R.drawable.bathroom);
                break;
            case Comm.ROOM_TYPE_KITCHEN:
                bmBackGround            = BitmapFactory.decodeResource(getResources(), R.drawable.kitchen);
                break;
            case Comm.ROOM_TYPE_BALCONY:
                bmBackGround            = BitmapFactory.decodeResource(getResources(), R.drawable.balcony);
                break;
            case Comm.ROOM_TYPE_YARD:
                bmBackGround            = BitmapFactory.decodeResource(getResources(), R.drawable.yard);
                break;
            default:
                bmBackGround            = BitmapFactory.decodeResource(getResources(), R.drawable.living_room);
        }

        //Make blur background
        if (bmBackGround!=null)
        {
            Blurry.with(this).sampling(1).from(bmBackGround).into(mIvRoomBlurBackground);
        }

        //Set viewpager adater
        adapterViewPager = new DevicePropertiesAdapter(getSupportFragmentManager(), pointCharacter);
        viewPagerDeviceProperties.setAdapter(adapterViewPager);

        tabDeviceProperties.setupWithViewPager(viewPagerDeviceProperties, true);
        //endregion

    }
}
