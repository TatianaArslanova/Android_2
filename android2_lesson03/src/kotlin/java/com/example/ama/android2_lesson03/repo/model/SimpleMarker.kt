package com.example.ama.android2_lesson03.repo.model

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

/**
 * Model with basic marker parameters for saving to favorites
 */
data class SimpleMarker(val address: String, val position: LatLng) {
    var title: String = address

    companion object {
        fun getFromMarker(marker: Marker): SimpleMarker = SimpleMarker(marker.snippet, marker.position)
        fun simpleMarkerWithTitle(title: String, address: String, position: LatLng): SimpleMarker {
            val marker = SimpleMarker(address, position)
            marker.title = title
            return marker
        }
    }

    override fun toString() = title
}
