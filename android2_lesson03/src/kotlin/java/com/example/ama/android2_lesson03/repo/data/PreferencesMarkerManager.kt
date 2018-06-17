package com.example.ama.android2_lesson03.repo.data

import android.content.Context
import com.example.ama.android2_lesson03.PocketMap
import com.example.ama.android2_lesson03.repo.data.base.MarkerManager
import com.example.ama.android2_lesson03.repo.model.SimpleMarker
import com.google.android.gms.maps.model.LatLng
import org.json.JSONException
import org.json.JSONObject

/**
 * Class for managing favorite markers, working with SharedPreferences
 */
class PreferencesMarkerManager : MarkerManager {

    companion object {
        const val PREFERENCES_FILE_NAME = "marker_preferences"
        const val MARKERS_KEY = "all_markers"

        const val MARKER_TITLE_KEY = "title"
        const val MARKER_LATITUDE_KEY = "latitude"
        const val MARKER_LONGITUDE_KEY = "longitude"
    }

    private val preferences = PocketMap.instance.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

    override fun getAllMarkers(): ArrayList<SimpleMarker> =
            getFromPreferences().mapNotNull { it -> simpleMarkerFromJson(it) } as ArrayList<SimpleMarker>


    override fun addMarker(marker: SimpleMarker) {
        val markerSet = getFromPreferences() as HashSet
        markerSet.add(simpleMarkerToJson(marker)!!)
        putInPreferences(markerSet)
    }

    override fun deleteMarker(marker: SimpleMarker) {
        val markerSet = getFromPreferences() as HashSet
        markerSet.remove(simpleMarkerToJson(marker)!!)
        putInPreferences(markerSet)
    }

    override fun updateMarker(marker: SimpleMarker, newName: String) {
        val markerSet = getFromPreferences() as HashSet
        markerSet.remove(simpleMarkerToJson(marker)!!)
        marker.title = newName
        markerSet.add(simpleMarkerToJson(marker)!!)
        putInPreferences(markerSet)
    }

    private fun getFromPreferences(): Set<String> =
            preferences.getStringSet(MARKERS_KEY, HashSet<String>())

    private fun putInPreferences(markerSet: Set<String>) {
        preferences.edit().clear().putStringSet(MARKERS_KEY, markerSet).apply()
    }

    private fun simpleMarkerToJson(marker: SimpleMarker): String? {
        val jsonObject = JSONObject()
        try {
            jsonObject.put(MARKER_TITLE_KEY, marker.title)
            jsonObject.put(MARKER_LATITUDE_KEY, marker.position.latitude)
            jsonObject.put(MARKER_LONGITUDE_KEY, marker.position.longitude)
            return jsonObject.toString()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    private fun simpleMarkerFromJson(json: String): SimpleMarker? {
        try {
            val jsonObject = JSONObject(json)
            return SimpleMarker(
                    jsonObject.getString(MARKER_TITLE_KEY),
                    LatLng(jsonObject.getDouble(MARKER_LATITUDE_KEY), jsonObject.getDouble(MARKER_LONGITUDE_KEY))
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

}