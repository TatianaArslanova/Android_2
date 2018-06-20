package com.example.ama.android2_lesson03.repo.data.state

import android.net.Uri
import com.google.android.gms.maps.model.LatLng

/**
 * Class for preparing Uri for sending to another apps
 */
class UriManager {

    companion object {
        const val URI_GEO_SHEME = "geo:"
        const val BASE_GEO_QUERY = "geo:0,0?q="
        const val ZOOM_PARAMETER = "?z="
    }

    var preparedUri = Uri.parse(BASE_GEO_QUERY)!!
        private set

    fun prepareUriByMarkerLocation(latLng: LatLng, label: String) {
        preparedUri = Uri.parse("$BASE_GEO_QUERY ${latLng.latitude},${latLng.longitude}($label)")
    }

    fun prepareUriByQuery(query: String) {
        preparedUri = Uri.parse("$BASE_GEO_QUERY$query")
    }

    fun prepareUriByCameraPosition(latLng: LatLng?, zoom: Float?) {
        if (latLng != null && zoom != null) {
            preparedUri = Uri.parse("$URI_GEO_SHEME${latLng.latitude},${latLng.longitude}$ZOOM_PARAMETER$zoom")
        }
    }
}