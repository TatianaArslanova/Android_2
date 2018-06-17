package com.example.ama.android2_lesson03.repo.data.base

import com.example.ama.android2_lesson03.repo.model.SimpleMarker

interface MarkerManager {
    fun getAllMarkers(): ArrayList<SimpleMarker>
    fun addMarker(marker: SimpleMarker)
    fun deleteMarker(marker: SimpleMarker)
    fun updateMarker(marker: SimpleMarker, newName: String)
}