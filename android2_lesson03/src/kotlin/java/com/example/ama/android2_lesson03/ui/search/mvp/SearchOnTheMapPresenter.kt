package com.example.ama.android2_lesson03.ui.search.mvp

import com.example.ama.android2_lesson03.ui.base.BasePresenter
import com.example.ama.android2_lesson03.ui.search.base.SearchOnTheMapView
import com.example.ama.android2_lesson03.ui.search.base.SearchPresenter

class SearchOnTheMapPresenter<T : SearchOnTheMapView> : BasePresenter<T>(), SearchPresenter<T> {

    override fun findAddressByQuery(query: String) {
    }

    override fun findAddressByLatLng(latitude: Double, longitude: Double) {
    }

    override fun sendQueryToGMapsApp(query: String) {
    }

    override fun findMyLocation() {
    }
}