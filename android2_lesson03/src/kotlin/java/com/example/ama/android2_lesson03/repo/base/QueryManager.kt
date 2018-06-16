package com.example.ama.android2_lesson03.repo.base

import android.net.Uri
import com.google.android.gms.maps.model.LatLng

interface QueryManager {
    fun prepareGMapUri(query: String)
    fun prepareGMapUri(latLng: LatLng)
    fun getPreparedUri(callback: OnUriPreparedCallback)
    fun getFullLocationName(query: String, callback: OnFullNamePreparedCallback)
    fun getFullLocationName(latLng: LatLng, callback: OnFullNamePreparedCallback)
    fun getMyLocation(callback: OnLocationSearchResultCallback)

    interface OnUriPreparedCallback {
        fun onSuccess(uri: Uri)
    }

    interface OnFullNamePreparedCallback {
        fun onSuccess(fullLocationName: String, latLng: LatLng, zoom: Float)
    }

    interface OnLocationSearchResultCallback {
        fun onLocationFound(latLng: LatLng)
        fun onNotFound()
        fun onPermissionRequired(permission: String, requestCode: Int)
    }
}