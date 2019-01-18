package com.ibs.android.ibssmarthome.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibs.android.ibssmarthome.Object.DeviceObject;
import com.ibs.android.ibssmarthome.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DeviceAdapter extends BaseAdapter {
    private final Context mContext;
    private final ArrayList<DeviceObject> devices;

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

            final ViewHolder viewHolder =new ViewHolder(txtDeviceNm,txtDevicePw,imgDeviceIcon);
            convertView.setTag(viewHolder);
        }

        final ViewHolder viewHolder = (ViewHolder)convertView.getTag();

        viewHolder.txtDeviceNm.setText(device.getName());

        if (!device.isPower()){
            Picasso.get().load(device.getIconOff()).fit().centerCrop().placeholder(R.drawable.loading).into(viewHolder.imgDeviceIcon);
            viewHolder.txtDevicePw.setText("Off");

            viewHolder.txtDeviceNm.setTextColor(ContextCompat.getColor(mContext,R.color.lv5Color));
            viewHolder.txtDevicePw.setVisibility(View.INVISIBLE);
        }
        else{
            Picasso.get().load(device.getIconOn()).fit().centerCrop().placeholder(R.drawable.loading).into(viewHolder.imgDeviceIcon);
            viewHolder.txtDevicePw.setText("On");
            viewHolder.txtDeviceNm.setTextColor(ContextCompat.getColor(mContext,R.color.lv7Color));
            viewHolder.txtDevicePw.setVisibility(View.VISIBLE);

        }
        return convertView;
    }

    private class ViewHolder {
        private final TextView txtDeviceNm;
        private final TextView txtDevicePw;
        private final ImageView imgDeviceIcon;

        public ViewHolder(TextView txtDeviceNm, TextView txtDevicePw, ImageView imgDeviceIcon) {
            this.txtDeviceNm = txtDeviceNm;
            this.txtDevicePw = txtDevicePw;
            this.imgDeviceIcon = imgDeviceIcon;
        }
    }

}
