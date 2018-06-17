package com.example.ama.android2_lesson03.repo.data.base

import android.location.Address
import com.google.android.gms.maps.model.LatLng

interface LocManager {
    fun findAddressByQuery(query: String): Address?
    fun findAddressByLatLng(latLng: LatLng): Address?
    fun findMyLocation(found: (latLng: LatLng, zoom: Float) -> Unit,
                       notFound: (message: String) -> Unit,
                       permissionRequired: (permission: String, requestCode: Int) -> Unit)
}