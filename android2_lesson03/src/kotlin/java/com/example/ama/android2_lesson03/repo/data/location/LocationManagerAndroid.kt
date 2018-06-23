package com.example.ama.android2_lesson03.repo.data.location

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationProvider
import android.os.Bundle
import android.util.Log
import com.example.ama.android2_lesson03.PocketMap
import com.example.ama.android2_lesson03.R
import com.example.ama.android2_lesson03.repo.SearchQueryManager
import com.example.ama.android2_lesson03.repo.data.base.BaseLocationManager
import com.example.ama.android2_lesson03.utils.FIND_MY_LOCATION_REQUEST
import com.example.ama.android2_lesson03.utils.FINE_LOCATION
import com.example.ama.android2_lesson03.utils.PermissionManager
import com.example.ama.android2_lesson03.utils.SUBSCRIBE_LOCATION_UPDATES
import com.google.android.gms.maps.model.LatLng

/**
 * Class for locating by android.location
 */
class LocationManagerAndroid : BaseLocationManager() {

    companion object {
        const val MIN_REQUEST_INTERVAL = 2000L
        const val MIN_DISTANCE_REQUEST = 0f
    }

    private val locationManager = PocketMap.instance.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private var listener: LocationListener? = null

    override fun findMyLocation(found: (latLng: LatLng, zoom: Float) -> Unit, notFound: (message: String) -> Unit, permissionRequired: (permission: String, requestCode: Int) -> Unit) {
        if (PermissionManager.checkPermission(PocketMap.instance, FINE_LOCATION)) {
            val location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
            if (location != null) {
                found.invoke(LatLng(location.latitude, location.longitude), SearchQueryManager.DEFAULT_ZOOM)
            } else {
                notFound.invoke(PocketMap.instance.getString(R.string.message_location_not_found))
            }
        } else {
            permissionRequired.invoke(FINE_LOCATION, FIND_MY_LOCATION_REQUEST)
        }
    }

    override fun subscribeOnLocationUpdates(locationFound: (location: Location) -> Unit, error: (message: String) -> Unit, permissionRequired: (permission: String, requestCode: Int) -> Unit) {
        if (PermissionManager.checkPermission(PocketMap.instance, FINE_LOCATION)) {
            registerListener(locationFound, error)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_REQUEST_INTERVAL, MIN_DISTANCE_REQUEST, listener)
        } else {
            permissionRequired.invoke(FINE_LOCATION, SUBSCRIBE_LOCATION_UPDATES)
        }
    }

    override fun unsubscribeOfLocationUpdates() {
        if (listener != null) {
            locationManager.removeUpdates(listener)
            listener = null
        }
    }

    private fun registerListener(locationFound: (location: Location) -> Unit,
                                 error: (message: String) -> Unit) {
        listener = object : LocationListener {
            override fun onLocationChanged(p0: Location?) {
                if (p0 != null) {
                    locationFound.invoke(p0)
                }
            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                when (p1) {
                    LocationProvider.AVAILABLE -> Log.d(javaClass.simpleName, "LocationProvider avaliable")
                    LocationProvider.TEMPORARILY_UNAVAILABLE -> Log.d(javaClass.simpleName, "LocationProvider temporarily unavailable")
                    LocationProvider.OUT_OF_SERVICE -> {
                        error.invoke(PocketMap.instance.getString(R.string.message_location_provider_out_of_service))
                        Log.d(javaClass.simpleName, "LocationProvider out of service")
                    }
                }
            }

            override fun onProviderEnabled(p0: String?) {
                Log.d(javaClass.simpleName, "LocationProvider enabled")
            }

            override fun onProviderDisabled(p0: String?) {
                Log.d(javaClass.simpleName, "LocationProvider disabled")
            }
        }
    }
}