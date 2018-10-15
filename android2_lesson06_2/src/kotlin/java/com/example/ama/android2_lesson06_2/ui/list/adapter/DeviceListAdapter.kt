package com.example.ama.android2_lesson06_2.ui.list.adapter

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class DeviceListAdapter(context: Context)
    : ArrayAdapter<BluetoothDevice>(context, android.R.layout.simple_list_item_1) {

    private var devices: ArrayList<BluetoothDevice> = ArrayList()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context)
                    .inflate(android.R.layout.simple_list_item_1, parent, false)
        }
        view?.findViewById<TextView>(android.R.id.text1)?.text = devices[position].name
        return view!!
    }

    fun addDevice(device: BluetoothDevice) {
        if (!devices.contains(device)) {
            devices.add(device)
            super.clear()
            addAll(devices)
            notifyDataSetChanged()
        }
    }

    override fun clear() {
        devices.clear()
        super.clear()
    }
}