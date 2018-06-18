package com.example.ama.android2_lesson03.ui.search.base

import android.net.Uri
import com.example.ama.android2_lesson03.ui.base.PocketMapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

interface SearchOnTheMapView : PocketMapView {
    fun showOnGMapsApp(uri: Uri)
    fun showOnInnerMap(markerTitle: String?, address: String, latLng: LatLng, zoom: Float)
    fun showMessage(message: String)
    fun zoomToLocation(latLng: LatLng, zoom: Float)
    fun requestPermission(permission: String, requestCode: Int)
    fun showEditDialog(dialogTitle: String, dialogMessage: String, marker: Marker)
}