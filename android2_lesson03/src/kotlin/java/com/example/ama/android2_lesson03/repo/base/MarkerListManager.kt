package com.example.ama.android2_lesson03.repo.base

import com.example.ama.android2_lesson03.repo.model.SimpleMarker

/**
 * Interface describes basic methods for managing markers by presenter's queries
 */
interface MarkerListManager {
    fun getAllMarkers(callback: (markers: ArrayList<SimpleMarker>) -> Unit)
    fun updateMarker(marker: SimpleMarker, newName: String, callback: (markers: ArrayList<SimpleMarker>) -> Unit)
    fun deleteMarker(marker: SimpleMarker, callback: (markers: ArrayList<SimpleMarker>) -> Unit)
    fun saveCurrentMarker(marker: SimpleMarker)
    fun prepareEditMarkerNameDialog(marker: SimpleMarker, callback: (dialogName: String, dialogMessage: String, marker: SimpleMarker)-> Unit)
}