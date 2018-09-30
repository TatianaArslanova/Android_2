package com.example.ama.android2_lesson03.repo.data.base

import android.location.Address
import android.location.Location
import com.google.android.gms.maps.model.LatLng

/**
 * Interface describes basic methods for work with locating
 */
interface LocManager {
    fun findAddressByQuery(query: String): Address?
    fun findAddressByLatLng(latLng: LatLng): Address?
    fun findMyLocation(found: (location: Location) -> Unit,
                       notFound: (message: String) -> Unit)

    fun subscribeOnLocationUpdates(locationFound: (location: Location) -> Unit,
                                   error: (message: String) -> Unit)

    fun unsubscribeOfLocationUpdates()
}