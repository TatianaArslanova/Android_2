package com.example.ama.android2_lesson03.repo.data.base

import android.location.Address
import android.location.Geocoder
import com.example.ama.android2_lesson03.PocketMap
import com.google.android.gms.maps.model.LatLng
import java.io.IOException

abstract class BaseLocationManager : LocManager {
    private val geocoder = Geocoder(PocketMap.instance)

    override fun findAddressByQuery(query: String): Address? {
        try {
            val addresses = geocoder.getFromLocationName(query, 1)
            if (addresses.isNotEmpty()) return addresses[0]
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    override fun findAddressByLatLng(latLng: LatLng): Address? {
        try {
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses.isNotEmpty()) return addresses[0]
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

}