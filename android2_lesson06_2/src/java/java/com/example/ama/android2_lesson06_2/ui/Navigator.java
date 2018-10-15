package com.example.ama.android2_lesson06_2.ui;

import android.bluetooth.BluetoothDevice;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.ama.android2_lesson06_2.R;
import com.example.ama.android2_lesson06_2.ui.details.DetailsListFragment;
import com.example.ama.android2_lesson06_2.ui.list.DeviceListFragment;

public class Navigator {

    public static void placeDeviceListFragment(MainActivity activity) {
        placeFragment(activity, R.id.fl_container, DeviceListFragment.newInstance(), false);
    }

    public static void plaseDetailsListFragment(MainActivity activity, BluetoothDevice arg) {
        placeFragment(activity, R.id.fl_container, DetailsListFragment.newInstance(arg), true);
    }

    private static void placeFragment(AppCompatActivity activity, int container, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(container, fragment, fragment.getClass().getSimpleName());
        if (addToBackStack) transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }
}
