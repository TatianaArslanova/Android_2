package com.example.ama.android2_lesson03.repo.data.location

import android.location.Location
import android.os.Looper
import com.example.ama.android2_lesson03.PocketMap
import com.example.ama.android2_lesson03.R
import com.example.ama.android2_lesson03.repo.SearchQueryManager
import com.example.ama.android2_lesson03.repo.data.base.BaseLocationManager
import com.example.ama.android2_lesson03.utils.FIND_MY_LOCATION_REQUEST
import com.example.ama.android2_lesson03.utils.FINE_LOCATION
import com.example.ama.android2_lesson03.utils.PermissionManager
import com.example.ama.android2_lesson03.utils.SUBSCRIBE_LOCATION_UPDATES
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

/**
 * Class for locating by Google Maps API
 */
class LocationManagerGoogle : BaseLocationManager() {

    companion object {
        const val REQUEST_INTERVAL = 3000L
    }

    private val client = LocationServices.getFusedLocationProviderClient(PocketMap.instance)
    private var listener: LocationCallback? = null

    override fun findMyLocation(found: (latLng: LatLng, zoom: Float) -> Unit, notFound: (message: String) -> Unit, permissionRequired: (permission: String, requestCode: Int) -> Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(PocketMap.instance)
        if (PermissionManager.checkPermission(PocketMap.instance, FINE_LOCATION)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    found.invoke(LatLng(location.latitude, location.longitude), SearchQueryManager.DEFAULT_ZOOM)
                } else {
                    notFound.invoke(PocketMap.instance.getString(R.string.message_location_not_found))
                }
            }
        } else {
            permissionRequired.invoke(FINE_LOCATION, FIND_MY_LOCATION_REQUEST)
        }
    }

    override fun subscribeOnLocationUpdates(locationFound: (location: Location) -> Unit, error: (message: String) -> Unit, permissionRequired: (permission: String, requestCode: Int) -> Unit) {
        if (PermissionManager.checkPermission(PocketMap.instance, FINE_LOCATION)) {
            registerLocationCallback(locationFound, error)
            client.requestLocationUpdates(
                    LocationRequest.create().setInterval(REQUEST_INTERVAL),
                    listener,
                    Looper.getMainLooper())
        } else {
            permissionRequired.invoke(FINE_LOCATION, SUBSCRIBE_LOCATION_UPDATES)
        }
    }

    override fun unsubscribeOfLocationUpdates() {
        if (listener != null) {
            client.removeLocationUpdates(listener)
            listener = null
        }
    }

    private fun registerLocationCallback(locationFound: (location: Location) -> Unit, error: (message: String) -> Unit) {
        listener = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                val locations = p0?.locations
                if (locations != null && locations.isNotEmpty()) {
                    locationFound.invoke(locations[0])
                } else {
                    error.invoke(PocketMap.instance.getString(R.string.message_location_not_found))
                }
            }
        }
    }
}