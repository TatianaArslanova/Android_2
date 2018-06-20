package com.example.ama.android2_lesson03.ui.markers.base

import com.example.ama.android2_lesson03.repo.data.model.SimpleMarker
import com.example.ama.android2_lesson03.ui.base.PocketMapView

interface MarkerView : PocketMapView {
    fun showMarkerList(markers: ArrayList<SimpleMarker>)
    fun showEditDialog(dialogTitle: String, dialogMessage: String, marker: SimpleMarker)
}