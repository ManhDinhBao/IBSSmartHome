package com.ibs.android.ibssmarthome.Adapter;

import android.content.Context;
import com.google.android.material.card.MaterialCardView;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibs.android.ibssmarthome.Comm;
import com.ibs.android.ibssmarthome.Object.DeviceObject;
import com.ibs.android.ibssmarthome.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DeviceAdapter extends BaseAdapter {
    private final Context mContext;
    private final ArrayList<DeviceObject> devices;
    private int strIconOn,strIconOff;

    public DeviceAdapter(Context context, ArrayList<DeviceObject> devices) {
        this.mContext = context;
        this.devices = devices;
    }

    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public Object getItem(int position) {
        return devices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DeviceObject device = devices.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.item_device, null);

            final ImageView imgDeviceIcon = convertView.findViewById(R.id.imgDeviceIcon);
            final TextView txtDeviceNm = convertView.findViewById(R.id.txtDeviceName);
            final TextView txtDevicePw = convertView.findViewById(R.id.txtDevicePw);
            final MaterialCardView cvDeviceItem = convertView.findViewById(R.id.cardview_deviceitem);



            final ViewHolder viewHolder = new ViewHolder(txtDeviceNm, txtDevicePw, imgDeviceIcon,cvDeviceItem);
            convertView.setTag(viewHolder);
        }

        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.txtDeviceNm.setText(device.getName());

        switch (device.getType().getID()){
            case Comm.DEVICE_TYPE_LIGHT:
                strIconOn = R.drawable.standard_light_on;
                strIconOff= R.drawable.standard_light_off;
                break;
            case Comm.DEVICE_TYPE_AC:
                strIconOn = R.drawable.air_conditioner_on;
                strIconOff= R.drawable.air_conditioner_off;
                break;
            case Comm.DEVICE_TYPE_EW:
                strIconOn = R.drawable.heater_on;
                strIconOff= R.drawable.heater_off;
                break;
            case Comm.DEVICE_TYPE_DOOR:
                strIconOn = R.drawable.door_open;
                strIconOff= R.drawable.door_lock;
                break;
            case Comm.DEVICE_TYPE_FAN:
                strIconOn = R.drawable.fan_on;
                strIconOff= R.drawable.fan_off;
                break;
            case Comm.DEVICE_TYPE_TV:
                strIconOn = R.drawable.tv_on;
                strIconOff= R.drawable.tv_off;
                break;
            case Comm.DEVICE_TYPE_WASHING_MACHINE:
                strIconOn = R.drawable.washing_maching_on;
                strIconOff= R.drawable.washing_maching_off;
                break;
            default:
                strIconOn = R.drawable.loading;
                strIconOff= R.drawable.loading;
        }

        if (!device.isPower()) {
            Picasso.get().load(device.getIconOff()).fit().centerCrop().placeholder(strIconOff).into(viewHolder.imgDeviceIcon);
            if (device.getType().getID().matches(Comm.DEVICE_TYPE_DOOR)) {
                viewHolder.txtDevicePw.setText("Close");
            } else {
                viewHolder.txtDevicePw.setText("Off");
                viewHolder.cvDeviceItem.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.lv3Color));
            }
            viewHolder.txtDeviceNm.setTextColor(ContextCompat.getColor(mContext, R.color.lv5Color));
            //viewHolder.txtDevicePw.setVisibility(View.INVISIBLE);
        } else {
            Picasso.get().load(device.getIconOn()).fit().centerCrop().placeholder(strIconOn).into(viewHolder.imgDeviceIcon);
            if (device.getType().getID().matches(Comm.DEVICE_TYPE_DOOR)) {
                viewHolder.txtDevicePw.setText("Open");
            } else {
                viewHolder.txtDevicePw.setText("On");
                viewHolder.cvDeviceItem.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.whiteColor));
            }
            viewHolder.txtDeviceNm.setTextColor(ContextCompat.getColor(mContext, R.color.lv7Color));
            //viewHolder.txtDevicePw.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private class ViewHolder {
        private final TextView txtDeviceNm;
        private final TextView txtDevicePw;
        private final ImageView imgDeviceIcon;
        private final MaterialCardView cvDeviceItem;

        public ViewHolder(TextView txtDeviceNm, TextView txtDevicePw, ImageView imgDeviceIcon, MaterialCardView cvDeviceItem) {
            this.txtDeviceNm = txtDeviceNm;
            this.txtDevicePw = txtDevicePw;
            this.imgDeviceIcon = imgDeviceIcon;
            this.cvDeviceItem = cvDeviceItem;
        }
    }

}
