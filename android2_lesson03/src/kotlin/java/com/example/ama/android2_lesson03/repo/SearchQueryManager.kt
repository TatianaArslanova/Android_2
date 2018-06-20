package com.example.ama.android2_lesson03.repo

import android.location.Address
import android.net.Uri
import com.example.ama.android2_lesson03.PocketMap
import com.example.ama.android2_lesson03.R
import com.example.ama.android2_lesson03.repo.base.SearchManager
import com.example.ama.android2_lesson03.repo.data.base.LocManager
import com.example.ama.android2_lesson03.repo.data.base.MarkerManager
import com.example.ama.android2_lesson03.repo.data.location.LocationManagerAndroid
import com.example.ama.android2_lesson03.repo.data.markers.PreferencesMarkerManager
import com.example.ama.android2_lesson03.repo.data.model.SimpleMarker
import com.example.ama.android2_lesson03.repo.data.state.SearchOnTheMapStateSaver
import com.example.ama.android2_lesson03.repo.data.state.UriManager
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import java.lang.StringBuilder

/**
 * Class for execute presenter's queries
 */
class SearchQueryManager : SearchManager {

    companion object {
        const val DEFAULT_ZOOM: Float = 15f
    }

    private val locManager: LocManager = LocationManagerAndroid()
    private val uriManager = UriManager()
    private val markerManager: MarkerManager = PreferencesMarkerManager()

    override fun prepareUriForGMaps(currentMarker: Marker?, cameraPosition: LatLng?, zoom: Float?, callback: (uri: Uri) -> Unit) {
        if (currentMarker == null) {
            uriManager.prepareUriByCameraPosition(cameraPosition, zoom)
        }
        callback.invoke(uriManager.preparedUri)
    }

    override fun getFullLocationName(query: String, callback: (fullLocationName: String, position: LatLng, zoom: Float) -> Unit) {
        val address = locManager.findAddressByQuery(query)
        if (address != null) {
            uriManager.prepareUriByQuery(query)
            callback.invoke(
                    buildFullName(address),
                    LatLng(address.latitude, address.longitude),
                    DEFAULT_ZOOM)

        }
    }

    override fun getFullLocationName(latLng: LatLng, callback: (fullLocationName: String, position: LatLng, zoom: Float) -> Unit) {
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

    override fun prepareSaveMarkerDialog(marker: Marker, success: (dialogTitle: String, dialogMessage: String, savingMarker: Marker) -> Unit) {
        val savingMarker = SimpleMarker.getFromMarker(marker)
        if (savingMarker != null) {
            success.invoke(PocketMap.instance.getString(R.string.save_marker_dialog_title),
                    if (!markerManager.isMarkerExists(savingMarker))
                        PocketMap.instance.getString(R.string.save_marker_dialog_message)
                    else
                        PocketMap.instance.getString(R.string.message_marker_already_exists),
                    marker)
        }
    }

    override fun saveMarkerToList(marker: Marker, customName: String, callback: (message: String) -> Unit) {
        val savingMarker = SimpleMarker.getFromMarker(marker)
        if (savingMarker != null) {
            if (markerManager.isMarkerExists(savingMarker)) {
                markerManager.updateMarker(savingMarker, customName)
            } else {
                markerManager.addMarker(savingMarker)
            }
            callback.invoke(PocketMap.instance.getString(R.string.marker_saved_message))
        }
    }

    override fun saveState(currentMarker: Marker?) {
        SearchOnTheMapStateSaver.saveCurrentMarker(SimpleMarker.getFromMarker(currentMarker))
    }

    override fun loadSavedState(onSuccess: (markerTitle: String, address: String, position: LatLng, zoom: Float) -> Unit) {
        val marker = SearchOnTheMapStateSaver.loadCurrentMarker()
        if (marker != null) {
            uriManager.prepareUriByMarkerLocation(marker.position, marker.address)
            onSuccess.invoke(marker.title, marker.address, marker.position, DEFAULT_ZOOM)
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