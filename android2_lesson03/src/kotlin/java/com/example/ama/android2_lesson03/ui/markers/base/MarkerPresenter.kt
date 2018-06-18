package com.example.ama.android2_lesson03.ui.markers.base

import com.example.ama.android2_lesson03.repo.model.SimpleMarker
import com.example.ama.android2_lesson03.ui.base.Presenter

interface MarkerPresenter<T : MarkerView> : Presenter<T> {
    fun getMarkerList()
    fun sendMarker(marker: SimpleMarker)
    fun editMarkerName(marker: SimpleMarker, newName: String)
    fun deleteMarker(marker: SimpleMarker)
    fun onUpdateMarker(marker: SimpleMarker)
}