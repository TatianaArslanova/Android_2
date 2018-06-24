package com.example.ama.android2_lesson03.ui.search.base

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

interface Controller {
    fun attachMap(map: GoogleMap)
    fun saveState()
    fun tuneMyLocation()
    fun showOnInnerMap(markerTitle: String?, address: String, latLng: LatLng)
    fun moveMapCamera(latLng: LatLng, zoom: Float, cameraAnimation: Boolean)
    fun prepareToGMaps()
}