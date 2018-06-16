package com.example.ama.android2_lesson03.ui.search

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ama.android2_lesson03.R
import com.example.ama.android2_lesson03.ui.search.base.SearchOnTheMapView
import com.example.ama.android2_lesson03.ui.search.base.SearchPresenter
import com.example.ama.android2_lesson03.ui.search.mvp.SearchOnTheMapPresenter
import com.google.android.gms.maps.model.LatLng

class SearchOnTheMapFragment : Fragment(), SearchOnTheMapView {

    private var presenter: SearchPresenter<SearchOnTheMapView>? = null

    companion object {
        fun newInstance() = SearchOnTheMapFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        presenter = SearchOnTheMapPresenter()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        presenter?.attachView(this)
        super.onStart()
    }

    override fun onStop() {
        presenter?.detachView()
        super.onStop()
    }

    override fun showOnGMapsApp(uri: Uri) {
    }

    override fun showOnInnerMap(address: String, latLng: LatLng, zoom: Float) {
    }

    override fun showErrorMessage(message: String) {
    }

    override fun zoomToLocation(latLng: LatLng) {
    }

    override fun requestPermission(permission: String, requestCode: Int) {
    }

}