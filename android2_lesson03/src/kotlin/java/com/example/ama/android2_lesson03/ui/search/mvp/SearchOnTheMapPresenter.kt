package com.example.ama.android2_lesson03.ui.search.mvp

import com.example.ama.android2_lesson03.repo.SearchQueryManager
import com.example.ama.android2_lesson03.repo.base.SearchManager
import com.example.ama.android2_lesson03.ui.base.BasePresenter
import com.example.ama.android2_lesson03.ui.search.base.SearchOnTheMapView
import com.example.ama.android2_lesson03.ui.search.base.SearchPresenter
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

/**
 * Presenter implementation for [SearchOnTheMapView]
 * @param <T> view for work with
 */
class SearchOnTheMapPresenter<T : SearchOnTheMapView> : BasePresenter<T>(), SearchPresenter<T> {

    private val queryManager: SearchManager = SearchQueryManager()

    override fun findAddressByQuery(query: String) {
        queryManager.getFullLocationName(query,
                { fullLocationName, position, zoom -> view?.showOnInnerMap(null, fullLocationName, position, zoom) })
    }

    override fun findAddressByLatLng(latLng: LatLng) {
        queryManager.getFullLocationName(latLng,
                { fullLocationName, position, zoom -> view?.showOnInnerMap(null, fullLocationName, position, zoom) })
    }

    override fun sendQueryToGMapsApp(currentMarker: Marker?, cameraPosition: LatLng?, zoom: Float?) {
        queryManager.prepareUriForGMaps(currentMarker, cameraPosition, zoom) { uri -> view?.showOnGMapsApp(uri) }
    }

    override fun findMyLocation() {
        queryManager.getMyLocation(
                found = { latLng, zoom -> view?.zoomToLocation(latLng, zoom) },
                notFound = { message -> view?.showMessage(message) },
                permissionRequired = { permission, requestCode -> view?.requestPermission(permission, requestCode) })
    }

    override fun saveMarker(marker: Marker, customName: String) {
        queryManager.saveMarkerToList(marker, customName)
        { message -> view?.showMessage(message) }
    }

    override fun onMarkerClick(marker: Marker) {
        queryManager.prepareSaveMarkerDialog(marker,
                { dialogTitle, dialogMessage, savingMarker -> view?.showEditDialog(dialogTitle, dialogMessage, savingMarker) })
    }

    override fun saveState(currentMarker: Marker?) {
        queryManager.saveState(currentMarker)
    }

    override fun loadSavedState() {
        queryManager.loadSavedState { markerTitle, address, position, zoom -> view?.showOnInnerMap(markerTitle, address, position, zoom, false) }
    }
}
