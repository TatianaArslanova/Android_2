package com.example.ama.android2_lesson03.ui.search

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.Toast
import com.example.ama.android2_lesson03.PocketMap
import com.example.ama.android2_lesson03.R
import com.example.ama.android2_lesson03.ui.Launcher
import com.example.ama.android2_lesson03.ui.MainActivity
import com.example.ama.android2_lesson03.ui.search.base.Controller
import com.example.ama.android2_lesson03.ui.search.base.SearchOnTheMapView
import com.example.ama.android2_lesson03.ui.search.base.SearchPresenter
import com.example.ama.android2_lesson03.ui.search.mvp.SearchOnTheMapPresenter
import com.example.ama.android2_lesson03.utils.*
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * Fragment for getting search user queries and showing on the map
 */
class SearchOnTheMapFragment : Fragment(), SearchOnTheMapView {

    private lateinit var mapView: MapView
    private var presenter: SearchPresenter<SearchOnTheMapView>? = null
    private var mapController: Controller? = null

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
        mapController = MapController(
                presenter!!,
                clearAddress = {
                    et_search.text.clear()
                    tv_address.text = ""
                },
                permissionRequired = { permission, requestCode ->
                    requestPermission(permission, requestCode)
                })
        addListeners()
        mapView = mv_main_map
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(
                { it ->
                    mapController?.attachMap(it)
                    mapController?.tuneMyLocation()
                })
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        presenter?.attachView(this)
        mapView.onStart()
        super.onStart()
    }

    override fun onResume() {
        presenter?.subscrineOnLocationUpdates()
        mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        presenter?.unsubscribeOfLocationUpdates()
        mapView.onPause()
        super.onPause()
    }

    override fun onStop() {
        mapView.onStop()
        mapController?.saveState()
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
                TUNE_MY_LOCATION_REQUEST -> mapController?.tuneMyLocation()
                FIND_MY_LOCATION_REQUEST -> presenter?.findMyLocation()
                SUBSCRIBE_LOCATION_UPDATES -> presenter?.subscrineOnLocationUpdates()
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

    override fun showOnInnerMap(markerTitle: String?, address: String, latLng: LatLng) {
        et_search.setText(address)
        tv_address.text = address
        mapController?.showOnInnerMap(markerTitle, address, latLng)
    }

    override fun showMessage(message: String) {
        Toast.makeText(PocketMap.instance, message, Toast.LENGTH_SHORT).show()
    }

    override fun moveMapCamera(latLng: LatLng, zoom: Float, cameraAnimation: Boolean) {
        mapController?.moveMapCamera(latLng, zoom, cameraAnimation)
    }

    override fun requestPermission(permission: String, requestCode: Int) {
        PermissionManager.requestPermission(this, permission, requestCode)
    }

    override fun showEditDialog(dialogTitle: String, dialogMessage: String, marker: Marker) {
        DialogLauncher.showEditDialog(activity, dialogTitle, dialogMessage, marker.title)
        { newName -> presenter?.saveMarker(marker, newName) }
    }

    private fun addListeners() {
        btn_search.setOnClickListener { startSearching() }
        btn_ongmap.setOnClickListener { mapController?.prepareToGMaps() }
    }

    private fun startSearching() {
        presenter?.findAddressByQuery(et_search.text.toString())
    }
}