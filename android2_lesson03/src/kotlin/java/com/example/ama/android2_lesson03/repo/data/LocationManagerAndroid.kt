package com.example.ama.android2_lesson03.repo.data

import android.content.Context
import android.location.LocationManager
import com.example.ama.android2_lesson03.PocketMap
import com.example.ama.android2_lesson03.R
import com.example.ama.android2_lesson03.repo.SearchQueryManager
import com.example.ama.android2_lesson03.repo.data.base.BaseLocationManager
import com.example.ama.android2_lesson03.utils.FIND_MY_LOCATION_REQUEST
import com.example.ama.android2_lesson03.utils.PermissionManager
import com.google.android.gms.maps.model.LatLng

class LocationManagerAndroid : BaseLocationManager() {
    override fun findMyLocation(found: (latLng: LatLng, zoom: Float) -> Unit, notFound: (message: String) -> Unit, permissionRequired: (permission: String, requestCode: Int) -> Unit) {
        val locationManager = PocketMap.instance.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (PermissionManager.checkPermission(PocketMap.instance, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
            val location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
            if (location != null) {
                found.invoke(LatLng(location.latitude, location.longitude), SearchQueryManager.DEFAULT_ZOOM)
            } else {
                notFound.invoke(PocketMap.instance.getString(R.string.message_location_not_found))
            }
        } else {
            permissionRequired.invoke(android.Manifest.permission.ACCESS_COARSE_LOCATION, FIND_MY_LOCATION_REQUEST)
        }
    }
}