package com.example.ama.android2_lesson03.repo.base

import android.net.Uri
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

/**
 * Interface describes methods for search manager queries
 */
interface SearchManager {
    fun prepareUriForGMaps(currentMarker: Marker?, cameraPosition: LatLng?, zoom: Float?,
                           callback: (uri: Uri) -> Unit)

    fun getFullLocationName(query: String,
                            callback: (fullLocationName: String, position: LatLng, zoom: Float) -> Unit)

    fun getFullLocationName(latLng: LatLng,
                            callback: (fullLocationName: String, position: LatLng, zoom: Float) -> Unit)

    fun getMyLocation(found: (latLng: LatLng, zoom: Float) -> Unit,
                      notFound: (message: String) -> Unit,
                      permissionRequired: (permission: String, requestCode: Int) -> Unit)

    fun prepareSaveMarkerDialog(marker: Marker,
                                success: (dialogTitle: String, dialogMessage: String, savingMarker: Marker) -> Unit)

    fun saveMarkerToList(marker: Marker, customName: String, callback: (message: String) -> Unit)

    fun saveState(currentMarker: Marker?)

    fun loadSavedState(onSuccess: (markerTitle: String, address: String, position: LatLng, zoom: Float) -> Unit)

    fun subscribeOnLocationUpdates(found: (latLng: LatLng, zoom: Float) -> Unit,
                                   notFound: (message: String) -> Unit,
                                   permissionRequired: (permission: String, requestCode: Int) -> Unit)

    fun unsubscribeOfLocationUpdates()
}