package com.example.ama.android2_lesson03.repo.data.state

import android.content.Context
import com.example.ama.android2_lesson03.PocketMap
import com.example.ama.android2_lesson03.repo.data.model.SimpleMarker

const val PREFERENCES_STATE_FILE_NAME = "SOTM_state_preferences"
const val CURRENT_MARKER_KEY = "current_marker"
const val NOT_FOUND = "not_found"

object SearchOnTheMapStateSaver {
    private val preferences = PocketMap.instance.getSharedPreferences(PREFERENCES_STATE_FILE_NAME, Context.MODE_PRIVATE)

    fun loadCurrentMarker(): SimpleMarker? =
            SimpleMarker.simpleMarkerFromJson(preferences.getString(CURRENT_MARKER_KEY, NOT_FOUND))

    fun saveCurrentMarker(currentMarker: SimpleMarker?) {
        val editor = preferences.edit()
        if (currentMarker != null) {
            editor.putString(CURRENT_MARKER_KEY, SimpleMarker.simpleMarkerToJson(currentMarker))
        } else {
            editor.remove(CURRENT_MARKER_KEY)
        }
        editor.apply()
    }
}