package com.example.ama.android2_lesson03.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import com.example.ama.android2_lesson03.PocketMap
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.Disposable

const val FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION

object PermissionManager {

    fun requestPermission(activity: Activity, permission: String, callback: (granted: Boolean) -> Unit): Disposable =
            RxPermissions(activity)
                    .request(permission)
                    .subscribe { it -> callback.invoke(it) }

    fun checkLocationPermission() =
            ContextCompat.checkSelfPermission(PocketMap.instance, FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
}