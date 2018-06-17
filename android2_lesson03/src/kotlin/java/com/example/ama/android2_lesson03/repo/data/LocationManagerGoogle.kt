package com.example.ama.android2_lesson03.repo.data

import com.example.ama.android2_lesson03.PocketMap
import com.example.ama.android2_lesson03.R
import com.example.ama.android2_lesson03.repo.SearchQueryManager
import com.example.ama.android2_lesson03.repo.data.base.BaseLocationManager
import com.example.ama.android2_lesson03.utils.FIND_MY_LOCATION_REQUEST
import com.example.ama.android2_lesson03.utils.PermissionManager
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

/**
 * Class for locating by Google Maps API
 */
class LocationManagerGoogle : BaseLocationManager() {
    override fun findMyLocation(found: (latLng: LatLng, zoom: Float) -> Unit, notFound: (message: String) -> Unit, permissionRequired: (permission: String, requestCode: Int) -> Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(PocketMap.instance)
        if (PermissionManager.checkPermission(PocketMap.instance, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    found.invoke(LatLng(location.latitude, location.longitude), SearchQueryManager.DEFAULT_ZOOM)
                } else {
                    notFound.invoke(PocketMap.instance.getString(R.string.message_location_not_found))
                }
            }
        } else {
            permissionRequired.invoke(android.Manifest.permission.ACCESS_COARSE_LOCATION, FIND_MY_LOCATION_REQUEST)
        }
    }
}