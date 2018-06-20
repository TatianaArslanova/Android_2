package com.example.ama.android2_lesson03.ui.search

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.example.ama.android2_lesson03.PocketMap
import com.example.ama.android2_lesson03.R
import com.example.ama.android2_lesson03.ui.Launcher
import com.example.ama.android2_lesson03.ui.MainActivity
import com.example.ama.android2_lesson03.ui.search.base.SearchOnTheMapView
import com.example.ama.android2_lesson03.ui.search.base.SearchPresenter
import com.example.ama.android2_lesson03.ui.search.mvp.SearchOnTheMapPresenter
import com.example.ama.android2_lesson03.utils.FIND_MY_LOCATION_REQUEST
import com.example.ama.android2_lesson03.utils.PermissionManager
import com.example.ama.android2_lesson03.utils.TUNE_MY_LOCATION_REQUEST
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * Fragment for getting search user queries and showing on the map
 */
class SearchOnTheMapFragment : Fragment(), SearchOnTheMapView {

    private lateinit var mapView: MapView

    private var map: GoogleMap? = null
    private var presenter: SearchPresenter<SearchOnTheMapView>? = null

    private var currentMarker: Marker? = null

    companion object {
        fun newInstance() = SearchOnTheMapFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        presenter = SearchOnTheMapPresenter()
        addListeners()
        mapView = mv_main_map
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync({ it ->
            map = it
            tuneMap()
            tuneMyLocation()
            presenter?.loadSavedState()
            if (currentMarker == null) {
                presenter?.findMyLocation()
            }
        })
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        presenter?.attachView(this)
        mapView.onStart()
        super.onStart()
    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onStop() {
        mapView.onStop()
        presenter?.saveState(currentMarker)
        presenter?.detachView()
        super.onStop()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        mapView.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        mapView.onLowMemory()
        super.onLowMemory()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                TUNE_MY_LOCATION_REQUEST -> tuneMyLocation()
                FIND_MY_LOCATION_REQUEST -> presenter?.findMyLocation()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
            when (item?.itemId) {
                R.id.mi_showsettings -> {
                    Launcher.runMarkerListFragment(activity as MainActivity, true)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    override fun showOnGMapsApp(uri: Uri) {
        Launcher.sendGoogleMapsIntent(activity as AppCompatActivity, uri)
    }

    override fun showOnInnerMap(markerTitle: String?, address: String, latLng: LatLng, zoom: Float, cameraAnimation: Boolean) {
        map?.clear()
        et_search.setText(address)
        tv_address.text = address
        if (cameraAnimation) {
            zoomToLocation(latLng, zoom)
        } else {
            setOnLocation(latLng, zoom)
        }
        if (markerTitle == null || markerTitle == address) {
            currentMarker = setMarkerOnTheMap(address, latLng)
        } else {
            currentMarker = setMarkerOnTheMapWithTitle(markerTitle, address, latLng)
        }
        currentMarker?.showInfoWindow()
    }

    override fun showMessage(message: String) {
        Launcher.showToast(message)
    }

    override fun zoomToLocation(latLng: LatLng, zoom: Float) {
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    override fun requestPermission(permission: String, requestCode: Int) {
        PermissionManager.requestPermission(this, permission, requestCode)
    }

    override fun showEditDialog(dialogTitle: String, dialogMessage: String, marker: Marker) {
        Launcher.showDialog(activity, dialogTitle, dialogMessage, marker.title)
        { newName -> presenter?.saveMarker(marker, newName) }
    }

    private fun addListeners() {
        btn_search.setOnClickListener { startSearching() }
        btn_ongmap.setOnClickListener { prepareToGmaps() }
    }

    private fun tuneMyLocation() {
        if (PermissionManager.checkPermission(PocketMap.instance, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
            map?.isMyLocationEnabled = true
            map?.uiSettings?.isMyLocationButtonEnabled = true
        } else {
            requestPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION, TUNE_MY_LOCATION_REQUEST)
        }
    }

    private fun setOnLocation(latLng: LatLng, zoom: Float) {
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    private fun tuneMap() {
        map?.uiSettings?.setAllGesturesEnabled(true)
        map?.uiSettings?.isZoomControlsEnabled = true
        map?.setOnMapLongClickListener { latLng -> presenter?.findAddressByLatLng(latLng) }
        map?.setOnMapClickListener { clearMap() }
        map?.setOnMarkerClickListener { marker ->
            presenter?.onMarkerClick(marker)
            true
        }
    }

    private fun startSearching() {
        presenter?.findAddressByQuery(et_search.text.toString())
    }

    private fun prepareToGmaps() {
        presenter?.sendQueryToGMapsApp(
                currentMarker,
                map?.cameraPosition?.target,
                map?.cameraPosition?.zoom)
    }

    private fun clearMap() {
        map?.clear()
        et_search.text.clear()
        tv_address.text = ""
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