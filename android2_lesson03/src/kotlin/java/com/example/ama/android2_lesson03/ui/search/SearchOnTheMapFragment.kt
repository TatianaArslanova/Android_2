package com.example.ama.android2_lesson03.ui.search

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.ama.android2_lesson03.PocketMap
import com.example.ama.android2_lesson03.ui.Launcher
import com.example.ama.android2_lesson03.ui.search.base.BaseMapFragment
import com.example.ama.android2_lesson03.ui.search.base.Controller
import com.example.ama.android2_lesson03.ui.search.base.SearchOnTheMapView
import com.example.ama.android2_lesson03.ui.search.base.SearchPresenter
import com.example.ama.android2_lesson03.ui.search.mvp.SearchOnTheMapPresenter
import com.example.ama.android2_lesson03.utils.DialogLauncher
import com.example.ama.android2_lesson03.utils.FINE_LOCATION
import com.example.ama.android2_lesson03.utils.PermissionManager
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * Fragment for getting search user queries and showing on the map
 */
class SearchOnTheMapFragment : BaseMapFragment(), SearchOnTheMapView {

    private var presenter: SearchPresenter<SearchOnTheMapView>? = null
    private var mapController: Controller? = null
    private var disposable: Disposable? = null

    companion object {
        fun newInstance() = SearchOnTheMapFragment()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        presenter = SearchOnTheMapPresenter()
        mapController = MapController(presenter!!) {
            et_search.text.clear()
            tv_address.text = ""
        }
        addListeners()
        super.onViewCreated(view, savedInstanceState)
        mapView.getMapAsync(
                { it ->
                    mapController?.attachMap(it)
                    disposable = PermissionManager.requestPermission(activity, FINE_LOCATION)
                    { isGranted -> mapController?.setLocationAccess(isGranted) }
                })
    }

    override fun onStart() {
        presenter?.attachView(this)
        super.onStart()
    }

    override fun onResume() {
        mapController?.onResume()
        super.onResume()
    }

    override fun onPause() {
        mapController?.onPause()
        super.onPause()
    }

    override fun onStop() {
        mapController?.saveState()
        presenter?.detachView()
        super.onStop()
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
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