package com.example.ama.android2_lesson03.repo.base

import android.net.Uri
import com.google.android.gms.maps.model.LatLng

interface QueryManager {
    fun getPreparedUri(isMarkerOnTheMap: Boolean, cameraPosition: LatLng?, zoom: Float?, callback: (uri: Uri) -> Unit)
    fun getFullLocationName(query: String, callback: (fullLocationName: String, latLng: LatLng, zoom: Float) -> Unit)
    fun getFullLocationName(latLng: LatLng, callback: (fullLocationName: String, latLng: LatLng, zoom: Float) -> Unit)
    fun getMyLocation(found: (latLng: LatLng, zoom: Float) -> Unit,
                      notFound: (message: String) -> Unit,
                      permissionRequired: (permission: String, requestCode: Int) -> Unit)
}