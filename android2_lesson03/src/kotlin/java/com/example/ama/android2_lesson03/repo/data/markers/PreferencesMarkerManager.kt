package com.example.ama.android2_lesson03.repo.data.markers

import android.content.Context
import com.example.ama.android2_lesson03.PocketMap
import com.example.ama.android2_lesson03.repo.data.base.MarkerManager
import com.example.ama.android2_lesson03.repo.data.model.SimpleMarker

/**
 * Class for managing favorite markers, working with SharedPreferences
 */
class PreferencesMarkerManager : MarkerManager {

    companion object {
        const val PREFERENCES_FILE_NAME = "marker_preferences"
        const val MARKERS_KEY = "all_markers"
    }

    private val preferences = PocketMap.instance.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

    override fun getAllMarkers(): ArrayList<SimpleMarker> =
            getFromPreferences().mapNotNull { it -> SimpleMarker.simpleMarkerFromJson(it) } as ArrayList<SimpleMarker>

    override fun addMarker(marker: SimpleMarker) {
        val markerSet = getFromPreferences() as HashSet
        markerSet.add(SimpleMarker.simpleMarkerToJson(marker)!!)
        putSetInPreferences(markerSet)
    }

    override fun deleteMarker(marker: SimpleMarker) {
        val markerSet = getFromPreferences() as HashSet
        markerSet.remove(SimpleMarker.simpleMarkerToJson(marker)!!)
        putSetInPreferences(markerSet)
    }

    override fun updateMarker(marker: SimpleMarker, newName: String) {
        val markerSet = getFromPreferences() as HashSet
        markerSet.remove(SimpleMarker.simpleMarkerToJson(marker)!!)
        marker.title = newName
        markerSet.add(SimpleMarker.simpleMarkerToJson(marker)!!)
        putSetInPreferences(markerSet)
    }

    override fun isMarkerExists(marker: SimpleMarker) =
            getAllMarkers().contains(marker)

    private fun getFromPreferences(): Set<String> =
            preferences.getStringSet(MARKERS_KEY, HashSet<String>())

    private fun putSetInPreferences(markerSet: Set<String>) {
        preferences.edit().clear().putStringSet(MARKERS_KEY, markerSet).apply()
    }
}