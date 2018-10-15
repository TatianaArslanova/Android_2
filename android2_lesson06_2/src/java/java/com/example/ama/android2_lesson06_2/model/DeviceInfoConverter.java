package com.example.ama.android2_lesson06_2.model;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.os.Build;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static android.bluetooth.BluetoothDevice.BOND_BONDED;
import static android.bluetooth.BluetoothDevice.BOND_BONDING;

public class DeviceInfoConverter {

    private static final String NAME = "Name";
    private static final String ADDRESS = "Address";
    private static final String TYPE = "Type";
    private static final String BOND_STATE = "Bond State";
    private static final String DEVICE_CLASS = "Device class";
    private static final String MAJOR_DEVICE_CLASS = "Major device class";

    private static final String UNKNOWN = "Unknown";

    public List<DeviceParam> getParams(BluetoothDevice device) {
        ArrayList<DeviceParam> params = new ArrayList<>();
        params.add(new DeviceParam(NAME, device.getName()));
        params.add(new DeviceParam(ADDRESS, device.getAddress()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            params.add(new DeviceParam(TYPE, getType(device)));
        }
        params.add(new DeviceParam(BOND_STATE, getBondState(device)));
        params.add(new DeviceParam(DEVICE_CLASS, getDeviceClass(device)));
        params.add(new DeviceParam(MAJOR_DEVICE_CLASS, getMajorDeviceClass(device)));
        return params;
    }

    @SuppressLint("InlinedApi")
    private String getType(BluetoothDevice device) {
        switch (device.getType()) {
            case BluetoothDevice.DEVICE_TYPE_CLASSIC:
                return "Classic";
            case BluetoothDevice.DEVICE_TYPE_DUAL:
                return "Dual";
            case BluetoothDevice.DEVICE_TYPE_LE:
                return "LowEnergy";
            default:
                return UNKNOWN;
        }
    }

    private String getBondState(BluetoothDevice device) {
        switch (device.getBondState()) {
            case BOND_BONDED:
                return "Bonded";
            case BOND_BONDING:
                return "Bonding";
            default:
                return "None";
        }
    }

    private String getDeviceClass(BluetoothDevice device) {
        Field[] fields = BluetoothClass.Device.class.getFields();
        for (Field o : fields) {
            try {
                if (device.getBluetoothClass().getDeviceClass() == (int) o.get(null)) {
                    return o.getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return UNKNOWN;
    }

    private String getMajorDeviceClass(BluetoothDevice device) {
        Field[] fields = BluetoothClass.Device.Major.class.getFields();
        for (Field o : fields) {
            try {
                if (device.getBluetoothClass().getMajorDeviceClass() == (int) o.get(null)) {
                    return o.getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return UNKNOWN;
    }
}

