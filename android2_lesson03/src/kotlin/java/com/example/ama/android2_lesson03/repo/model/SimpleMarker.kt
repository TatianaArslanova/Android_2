package com.example.ama.android2_lesson03.repo.model

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

/**
 * Model with basic marker parameters for saving to favorites
 */
data class SimpleMarker(var title: String, val position: LatLng) {

    companion object {
        fun getFromMarker(marker: Marker): SimpleMarker = SimpleMarker(marker.title, marker.position)
    }

    override fun toString() = title
}
