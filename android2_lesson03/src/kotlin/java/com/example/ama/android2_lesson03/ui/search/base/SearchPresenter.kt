package com.example.ama.android2_lesson03.ui.search.base

import com.example.ama.android2_lesson03.ui.base.Presenter

interface SearchPresenter<T : SearchOnTheMapView> : Presenter<T> {
    fun findAddressByQuery(query: String)
    fun findAddressByLatLng(latitude: Double, longitude: Double)
    fun sendQueryToGMapsApp(query: String)
    fun findMyLocation()
}