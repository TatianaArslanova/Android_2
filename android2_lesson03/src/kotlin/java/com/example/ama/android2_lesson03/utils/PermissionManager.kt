package com.example.ama.android2_lesson03.utils

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

const val FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION
const val TUNE_MY_LOCATION_REQUEST = 1
const val FIND_MY_LOCATION_REQUEST = 2
const val SUBSCRIBE_LOCATION_UPDATES = 3

object PermissionManager {

    fun checkPermission(context: Context, permission: String) =
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    fun requestPermission(fragment: Fragment, permission: String, requestCode: Int) {
        fragment.requestPermissions(arrayOf(permission), requestCode)
    }
}