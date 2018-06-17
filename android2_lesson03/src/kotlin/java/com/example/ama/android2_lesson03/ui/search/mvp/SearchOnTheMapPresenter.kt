package com.example.ama.android2_lesson03.ui.search.mvp

import com.example.ama.android2_lesson03.PocketMap
import com.example.ama.android2_lesson03.repo.base.QueryManager
import com.example.ama.android2_lesson03.ui.base.BasePresenter
import com.example.ama.android2_lesson03.ui.search.base.SearchOnTheMapView
import com.example.ama.android2_lesson03.ui.search.base.SearchPresenter
import com.google.android.gms.maps.model.LatLng

class SearchOnTheMapPresenter<T : SearchOnTheMapView> : BasePresenter<T>(), SearchPresenter<T> {

    private val queryManager: QueryManager = PocketMap.queryManager

    override fun findAddressByQuery(query: String) {
        queryManager.getFullLocationName(query,
                { fullLocationName, latLng, zoom -> view?.showOnInnerMap(fullLocationName, latLng, zoom) })
    }

    override fun findAddressByLatLng(latLng: LatLng) {
        queryManager.getFullLocationName(latLng,
                { fullLocationName, latLng, zoom -> view?.showOnInnerMap(fullLocationName, latLng, zoom) })
    }

    override fun sendQueryToGMapsApp(isMarkerOnTheMap: Boolean, cameraPosition: LatLng?, zoom: Float?) {
        queryManager.getPreparedUri(isMarkerOnTheMap, cameraPosition, zoom) { uri -> view?.showOnGMapsApp(uri) }
    }

    override fun findMyLocation() {
        queryManager.getMyLocation(
                found = { latLng, zoom -> view?.zoomToLocation(latLng, zoom) },
                notFound = { message -> view?.showMessage(message) },
                permissionRequired = { permission, requestCode -> view?.requestPermission(permission, requestCode) })
    }
}