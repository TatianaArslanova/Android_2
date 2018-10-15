package com.example.ama.android2_lesson06_2.ui

import android.bluetooth.BluetoothDevice
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.ama.android2_lesson06_2.R
import com.example.ama.android2_lesson06_2.ui.details.DetailsListFragment
import com.example.ama.android2_lesson06_2.ui.list.DeviceListFragment

object Navigator {

    fun placeDeviceListFragment(activity: MainActivity) {
        placeFragment(activity, R.id.fl_container, DeviceListFragment.newInstance(), false)
    }

    fun placeDetailsListFragment(activity: MainActivity, arg: BluetoothDevice) {
        placeFragment(activity, R.id.fl_container, DetailsListFragment.newInstance(arg), true)
    }

    private fun placeFragment(activity: AppCompatActivity, container: Int, fragment: Fragment, addToBackStack: Boolean) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(container, fragment, fragment::class.java.simpleName)
        if (addToBackStack) transaction.addToBackStack(fragment::class.java.simpleName)
        transaction.commit()
    }
}