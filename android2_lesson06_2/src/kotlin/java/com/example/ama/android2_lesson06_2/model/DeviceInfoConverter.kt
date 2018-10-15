package com.example.ama.android2_lesson06_2.model

import android.annotation.SuppressLint
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.BOND_BONDED
import android.bluetooth.BluetoothDevice.BOND_BONDING
import android.os.Build
import java.util.*

object DeviceInfoConverter {

    private const val NAME = "Name"
    private const val ADDRESS = "Address"
    private const val TYPE = "Type"
    private const val BOND_STATE = "Bond State"
    private const val DEVICE_CLASS = "Device class"
    private const val MAJOR_DEVICE_CLASS = "Major device class"

    private const val UNKNOWN = "Unknown"
    private const val CLASSIC = "Classic"
    private const val DUAL = "Dual"
    private const val LOW_ENERGY = "LowEnergy"
    private const val BONDED = "Bonded"
    private const val BONDING = "Bonding"
    private const val NONE = "None"

    fun getParams(device: BluetoothDevice): List<DeviceParam> {
        val params = ArrayList<DeviceParam>()
        params.add(DeviceParam(NAME, device.name))
        params.add(DeviceParam(ADDRESS, device.address))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            params.add(DeviceParam(TYPE, getType(device)))
        }
        params.add(DeviceParam(BOND_STATE, getBondState(device)))
        params.add(DeviceParam(DEVICE_CLASS, getDeviceClass(device)))
        params.add(DeviceParam(MAJOR_DEVICE_CLASS, getMajorDeviceClass(device)))
        return params
    }

    @SuppressLint("InlinedApi")
    private fun getType(device: BluetoothDevice) =
            when (device.type) {
                BluetoothDevice.DEVICE_TYPE_CLASSIC -> CLASSIC
                BluetoothDevice.DEVICE_TYPE_DUAL -> DUAL
                BluetoothDevice.DEVICE_TYPE_LE -> LOW_ENERGY
                else -> UNKNOWN
            }

    private fun getBondState(device: BluetoothDevice) =
            when (device.bondState) {
                BOND_BONDED -> BONDED
                BOND_BONDING -> BONDING
                else -> NONE
            }

    private fun getDeviceClass(device: BluetoothDevice): String {
        val fields = BluetoothClass.Device::class.java.fields
        for (field in fields) {
            try {
                if (device.bluetoothClass.deviceClass == field.get(null) as Int) {
                    return field.name
                }
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }
        return UNKNOWN
    }

    private fun getMajorDeviceClass(device: BluetoothDevice): String {
        val fields = BluetoothClass.Device.Major::class.java.fields
        for (field in fields) {
            try {
                if (device.bluetoothClass.majorDeviceClass == field.get(null) as Int) {
                    return field.name
                }
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
        return UNKNOWN
    }
}