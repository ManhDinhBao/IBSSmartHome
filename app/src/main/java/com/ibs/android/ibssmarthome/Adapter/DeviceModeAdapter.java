package com.ibs.android.ibssmarthome.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibs.android.ibssmarthome.R;

import java.util.ArrayList;

public class DeviceModeAdapter extends BaseAdapter {
    private final Context mContext;
    private final ArrayList<String> modes;

    public DeviceModeAdapter(Context context, ArrayList<String> modes) {
        this.mContext = context;
        this.modes = modes;
    }
    @Override
    public int getCount() {
        return modes.size();
    }

    @Override
    public Object getItem(int position) {
        return modes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String strDeviceMode=modes.get(position);
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.item_device_mode, null);

            TextView tvDeviceMode = convertView.findViewById(R.id.textview_devicemode_name);

            final ViewHolder viewHolder =new ViewHolder(tvDeviceMode);
            convertView.setTag(viewHolder);

            tvDeviceMode.setText(strDeviceMode);
        }
        return convertView;
    }

    private class ViewHolder {
        private final TextView tvDeviceMode;

        public ViewHolder(TextView tvDeviceMode) {
            this.tvDeviceMode = tvDeviceMode;
        }
    }
}
