package com.example.ama.android2_lesson03.ui.search.base

import com.example.ama.android2_lesson03.ui.base.Presenter
import com.google.android.gms.maps.model.LatLng

interface SearchPresenter<T : SearchOnTheMapView> : Presenter<T> {
    fun findAddressByQuery(query: String)
    fun findAddressByLatLng(latLng: LatLng)
    fun sendQueryToGMapsApp(isMarkerOnTheMap: Boolean, cameraPosition: LatLng?, zoom: Float?)
    fun findMyLocation()
}