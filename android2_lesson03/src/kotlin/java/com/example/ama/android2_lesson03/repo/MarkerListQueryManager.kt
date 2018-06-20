package com.example.ama.android2_lesson03.repo

import com.example.ama.android2_lesson03.PocketMap
import com.example.ama.android2_lesson03.R
import com.example.ama.android2_lesson03.repo.base.MarkerListManager
import com.example.ama.android2_lesson03.repo.data.base.MarkerManager
import com.example.ama.android2_lesson03.repo.data.markers.PreferencesMarkerManager
import com.example.ama.android2_lesson03.repo.data.model.SimpleMarker
import com.example.ama.android2_lesson03.repo.data.state.SearchOnTheMapStateSaver

class MarkerListQueryManager : MarkerListManager {

    private val markerManager: MarkerManager = PreferencesMarkerManager()

    override fun prepareEditMarkerNameDialog(marker: SimpleMarker, callback: (dialogName: String, dialogMessage: String, editingMarker: SimpleMarker) -> Unit) {
        callback.invoke(
                PocketMap.instance.getString(R.string.edit_dialog_title),
                PocketMap.instance.getString(R.string.edit_dialog_message_marker_name),
                marker)
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

    override fun saveCurrentMarker(marker: SimpleMarker) {
        SearchOnTheMapStateSaver.saveCurrentMarker(marker)
    }
}