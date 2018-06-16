package com.example.ama.android2_lesson03.ui.search.base

import android.net.Uri
import com.example.ama.android2_lesson03.ui.base.PocketMapView
import com.google.android.gms.maps.model.LatLng

interface SearchOnTheMapView : PocketMapView {
    fun showOnGMapsApp(uri: Uri)
    fun showOnInnerMap(address: String, latLng: LatLng, zoom: Float)
    fun showErrorMessage(message: String)
    fun zoomToLocation(latLng: LatLng)
    fun requestPermission(permission: String, requestCode: Int)
}