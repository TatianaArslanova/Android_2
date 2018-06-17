package com.example.ama.android2_lesson03.repo

import android.location.Address
import android.net.Uri
import com.example.ama.android2_lesson03.PocketMap
import com.example.ama.android2_lesson03.R
import com.example.ama.android2_lesson03.repo.base.MarkerListManager
import com.example.ama.android2_lesson03.repo.base.SearchManager
import com.example.ama.android2_lesson03.repo.data.LocationManagerAndroid
import com.example.ama.android2_lesson03.repo.data.PreferencesMarkerManager
import com.example.ama.android2_lesson03.repo.data.UriManager
import com.example.ama.android2_lesson03.repo.data.base.LocManager
import com.example.ama.android2_lesson03.repo.data.base.MarkerManager
import com.example.ama.android2_lesson03.repo.model.SimpleMarker
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import java.lang.StringBuilder

/**
 * Class for execute presenter's queries
 */
class SearchQueryManager : SearchManager, MarkerListManager {

    companion object {
        const val DEFAULT_ZOOM: Float = 15f
    }

    private val locManager: LocManager = LocationManagerAndroid()
    private val uriManager = UriManager()
    private val markerManager: MarkerManager = PreferencesMarkerManager()

    private var savedMarker: SimpleMarker? = null

    override fun getPreparedUri(isMarkerOnTheMap: Boolean, cameraPosition: LatLng?, zoom: Float?, callback: (uri: Uri) -> Unit) {
        if (!isMarkerOnTheMap) {
            uriManager.prepareUriByCameraPosition(cameraPosition, zoom)
        }
        callback.invoke(uriManager.preparedUri)
    }

    override fun getFullLocationName(query: String, callback: (fullLocationName: String, latLng: LatLng, zoom: Float) -> Unit) {
        val address = locManager.findAddressByQuery(query)
        if (address != null) {
            uriManager.prepareUriByQuery(query)
            callback.invoke(
                    buildFullName(address),
                    LatLng(address.latitude, address.longitude),
                    DEFAULT_ZOOM)

        }
    }

    override fun getFullLocationName(latLng: LatLng, callback: (fullLocationName: String, latLng: LatLng, zoom: Float) -> Unit) {
        val address = locManager.findAddressByLatLng(latLng)
        if (address != null) {
            val fullLocationName = buildFullName(address)
            uriManager.prepareUriByMarkerLocation(latLng, fullLocationName)
            callback.invoke(
                    fullLocationName,
                    latLng,
                    DEFAULT_ZOOM)
        }
    }

    override fun getMyLocation(found: (latLng: LatLng, zoom: Float) -> Unit,
                               notFound: (message: String) -> Unit,
                               permissionRequired: (permission: String, requestCode: Int) -> Unit) {
        locManager.findMyLocation(found, notFound, permissionRequired)
    }

    override fun saveCurrentMarker(marker: SimpleMarker) {
        savedMarker = marker
    }

    override fun prepareSaveMarkerDialog(marker: Marker, callback: (title: String, message: String, marker: Marker) -> Unit) {
        callback.invoke(PocketMap.instance.getString(R.string.save_marker_dialog_title),
                PocketMap.instance.getString(R.string.save_marker_dialog_message),
                marker
        )
    }

    override fun saveMarkerToList(marker: Marker, callback: (message: String) -> Unit) {
        markerManager.addMarker(SimpleMarker.getFromMarker(marker))
        callback.invoke(PocketMap.instance.getString(R.string.marker_saved_message))
    }


    override fun getAllMarkers(callback: (markers: ArrayList<SimpleMarker>) -> Unit) {
        callback.invoke(markerManager.getAllMarkers())
    }

    override fun updateMarker(marker: SimpleMarker, newName: String, callback: (markers: ArrayList<SimpleMarker>) -> Unit) {
        markerManager.updateMarker(marker, newName)
        getAllMarkers(callback)
    }

    override fun deleteMarker(marker: SimpleMarker, callback: (markers: ArrayList<SimpleMarker>) -> Unit) {
        markerManager.deleteMarker(marker)
        getAllMarkers(callback)
    }

    override fun getCurrentMarker(found: (title: String, position: LatLng, zoom: Float) -> Unit, notFound: () -> Unit) {
        if (savedMarker != null) {
            found.invoke(
                    savedMarker?.title!!,
                    savedMarker?.position!!,
                    DEFAULT_ZOOM)
        } else {
            notFound.invoke()
        }
    }

    private fun buildFullName(address: Address): String {
        val builder = StringBuilder()
        for (i in 0..address.maxAddressLineIndex) {
            if (builder.isNotEmpty()) builder.append(",")
            builder.append(address.getAddressLine(i))
        }
        return builder.toString()
    }
}