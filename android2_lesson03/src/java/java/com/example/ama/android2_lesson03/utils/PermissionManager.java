package com.example.ama.android2_lesson03.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

public class PermissionManager {

    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final int TUNE_MY_LOCATION_REQUEST = 1;
    public static final int SUBSCRIBE_ON_LOCATION_UPDATES = 2;
    public static final int FIND_MY_LOCATION_REQUEST = 3;

    public static boolean checkPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Fragment fragment, String permission, int requestCode) {
        fragment.requestPermissions(new String[]{permission}, requestCode);
    }
}
