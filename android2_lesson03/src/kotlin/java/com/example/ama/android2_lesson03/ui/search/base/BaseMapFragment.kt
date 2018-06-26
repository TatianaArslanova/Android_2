package com.example.ama.android2_lesson03.ui.search.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.ama.android2_lesson03.R
import com.example.ama.android2_lesson03.ui.Launcher
import com.example.ama.android2_lesson03.ui.MainActivity
import com.google.android.gms.maps.MapView
import kotlinx.android.synthetic.main.fragment_search.*

abstract class BaseMapFragment : Fragment() {
    protected lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mapView = mv_main_map
        mapView.onCreate(savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
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

}