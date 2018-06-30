package com.example.ama.android2_lesson03.ui.search

import com.example.ama.android2_lesson03.ui.search.base.Controller
import com.example.ama.android2_lesson03.ui.search.base.SearchOnTheMapView
import com.example.ama.android2_lesson03.ui.search.base.SearchPresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapController(
        private val presenter: SearchPresenter<SearchOnTheMapView>,
        private val clearAddress: () -> Unit)
    : Controller {

    private var map: GoogleMap? = null
    private var currentMarker: Marker? = null
    private var isLocationAccessEnabled = false

    override fun attachMap(map: GoogleMap) {
        this.map = map
        tuneMap()
        presenter.loadSavedState()
    }

    override fun saveState() {
        presenter.saveState(currentMarker)
    }

    override fun onResume() {
        if (isLocationAccessEnabled) {
            presenter.subscribeOnLocationUpdates()
        }
    }

    override fun onPause() {
        if (isLocationAccessEnabled) {
            presenter.unsubscribeOfLocationUpdates()
        }
    }

    override fun setLocationAccess(enabled: Boolean) {
        isLocationAccessEnabled = enabled
        tuneMyLocation()
    }

    override fun showOnInnerMap(markerTitle: String?, address: String, latLng: LatLng) {
        map?.clear()
        currentMarker = if (markerTitle == null || markerTitle == address) {
            setMarkerOnTheMap(address, latLng)
        } else {
            setMarkerOnTheMapWithTitle(markerTitle, address, latLng)
        }
        currentMarker?.showInfoWindow()
    }

    override fun moveMapCamera(latLng: LatLng, zoom: Float, cameraAnimation: Boolean) {
        if (cameraAnimation) {
            zoomToLocation(latLng, zoom)
        } else {
            setOnLocation(latLng, zoom)
        }
    }

    override fun prepareToGMaps() {
        presenter.sendQueryToGMapsApp(
                currentMarker,
                map?.cameraPosition?.target,
                map?.cameraPosition?.zoom)
    }

    private fun tuneMap() {
        map?.uiSettings?.setAllGesturesEnabled(true)
        map?.uiSettings?.isZoomControlsEnabled = true
        map?.setOnMapLongClickListener { latLng -> presenter.findAddressByLatLng(latLng) }
        map?.setOnMapClickListener { clearMap() }
        map?.setOnMarkerClickListener { marker ->
            presenter.onMarkerClick(marker)
            true
        }
    }

    @SuppressWarnings("MissingPermission")
    private fun tuneMyLocation() {
        if (isLocationAccessEnabled) {
            map?.isMyLocationEnabled = true
            map?.uiSettings?.isMyLocationButtonEnabled = true
            if (currentMarker == null) {
                presenter.findMyLocation()
            }
        }
    }

    private fun setOnLocation(latLng: LatLng, zoom: Float) {
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    private fun zoomToLocation(latLng: LatLng, zoom: Float) {
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    private fun clearMap() {
        map?.clear()
        clearAddress.invoke()
        currentMarker = null
    }

    private fun setMarkerOnTheMap(address: String, position: LatLng): Marker? =
            map?.addMarker(MarkerOptions()
                    .title(address)
                    .position(position))

    private fun setMarkerOnTheMapWithTitle(title: String, address: String, position: LatLng): Marker? =
            map?.addMarker(MarkerOptions()
                    .title(title)
                    .snippet(address)
                    .position(position))
}