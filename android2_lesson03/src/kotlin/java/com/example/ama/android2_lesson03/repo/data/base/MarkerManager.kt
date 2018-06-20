package com.example.ama.android2_lesson03.repo.data.base

import com.example.ama.android2_lesson03.repo.data.model.SimpleMarker

/**
 * Interface describes basic methods for managing favorites markers
 */
interface MarkerManager {
    fun getAllMarkers(): ArrayList<SimpleMarker>
    fun addMarker(marker: SimpleMarker)
    fun deleteMarker(marker: SimpleMarker)
    fun updateMarker(marker: SimpleMarker, newName: String)
    fun isMarkerExists(marker: SimpleMarker): Boolean
}