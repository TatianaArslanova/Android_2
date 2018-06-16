package com.example.ama.android2_lesson03.repo.data

import android.location.Address
import com.example.ama.android2_lesson03.repo.base.QueryManager
import com.google.android.gms.maps.model.LatLng

interface LocManager {
    fun findAddressByQuery(query: String): Address
    fun findAddressByLatLng(latLng: LatLng): Address
    fun fingMyLocation(callback: QueryManager.OnLocationSearchResultCallback)
}