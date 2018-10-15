package com.example.ama.android2_lesson06_2.ui.list.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DeviceListAdapter extends ArrayAdapter<BluetoothDevice> {
    private ArrayList<BluetoothDevice> devices = new ArrayList<>();

    public DeviceListAdapter(@NonNull Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(devices.get(position).getName());
        return convertView;
    }

    public void addDevice(BluetoothDevice device) {
        if (!devices.contains(device)) {
            devices.add(device);
            super.clear();
            addAll(devices);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        devices.clear();
        super.clear();
    }
}
