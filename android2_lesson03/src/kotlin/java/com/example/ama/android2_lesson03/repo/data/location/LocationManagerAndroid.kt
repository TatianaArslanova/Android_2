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

    @SuppressWarnings("MissingPermission")
    override fun findMyLocation(found: (location: Location) -> Unit, notFound: (message: String) -> Unit) {
        val location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
        if (location != null) {
            found.invoke(location)
        } else {
            notFound.invoke(PocketMap.instance.getString(R.string.message_location_not_found))
        }
    }

    @SuppressWarnings("MissingPermission")
    override fun subscribeOnLocationUpdates(locationFound: (location: Location) -> Unit, error: (message: String) -> Unit) {
        registerListener(locationFound, error)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_REQUEST_INTERVAL, MIN_DISTANCE_REQUEST, listener)
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
                    LocationProvider.AVAILABLE -> Log.d("onStatusChanged", "LocationProvider avaliable")
                    LocationProvider.TEMPORARILY_UNAVAILABLE -> Log.d("onStatusChanged", "LocationProvider temporarily unavailable")
                    LocationProvider.OUT_OF_SERVICE -> {
                        error.invoke(PocketMap.instance.getString(R.string.message_location_provider_out_of_service))
                        Log.d("onStatusChanged", "LocationProvider out of service")
                    }
                }
            }

            override fun onProviderEnabled(p0: String?) {
                Log.d("onProviderEnabled", "LocationProvider enabled")
            }

            override fun onProviderDisabled(p0: String?) {
                Log.d("onProviderDisabled", "LocationProvider disabled")
            }
        }
    }
}