package com.example.ama.android2_lesson03.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.example.ama.android2_lesson03.PocketMap;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class PermissionManager {

    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    public static Disposable requestPermission(Activity activity, String permission, final OnGrantResult callback) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        return rxPermissions
                .request(permission)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        callback.sendResult(aBoolean);
                    }
                });
    }

    public static boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(PocketMap.getInstance(), FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    public interface OnGrantResult {
        void sendResult(boolean granted);
    }
}
