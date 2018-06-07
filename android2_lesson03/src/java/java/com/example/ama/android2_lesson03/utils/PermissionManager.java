package com.example.ama.android2_lesson03.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

public class PermissionManager {

    public static boolean checkPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Fragment fragment, String permission, int requestCode) {
        fragment.requestPermissions(new String[]{permission}, requestCode);
    }
}
