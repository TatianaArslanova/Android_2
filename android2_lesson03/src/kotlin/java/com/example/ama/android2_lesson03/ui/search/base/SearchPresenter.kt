package com.example.ama.android2_lesson03.ui.search.base

import com.example.ama.android2_lesson03.ui.base.Presenter
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

interface SearchPresenter<T : SearchOnTheMapView> : Presenter<T> {
    fun findAddressByQuery(query: String)
    fun findAddressByLatLng(latLng: LatLng)
    fun sendQueryToGMapsApp(currentMarker: Marker?, cameraPosition: LatLng?, zoom: Float?)
    fun findMyLocation()
    fun saveMarker(marker: Marker, customName: String)
    fun onMarkerClick(marker: Marker)
    fun saveState(currentMarker: Marker?)
    fun loadSavedState()
    fun subscribeOnLocationUpdates()
    fun unsubscribeOfLocationUpdates()
}