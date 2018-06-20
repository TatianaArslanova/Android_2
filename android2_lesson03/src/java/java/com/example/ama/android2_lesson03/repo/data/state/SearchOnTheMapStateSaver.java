package com.example.ama.android2_lesson03.repo.data.state;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.repo.data.model.SimpleMarker;

public class SearchOnTheMapStateSaver {
    public static final String NOT_FOUND = "not_found";
    private static final String PREFERENCES_FILE_NAME = "SOTM_state_preferences";
    private static final String CURRENT_MARKER_KEY = "current_marker";

    private static SharedPreferences preferences;

    static {
        preferences = PocketMap.getInstance().getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static SimpleMarker loadCurrentMarker() {
        return SimpleMarker.simpleMarkerFromJson(preferences.getString(CURRENT_MARKER_KEY, NOT_FOUND));
    }

    public static void saveCurrentMarker(SimpleMarker currentMarker) {
        SharedPreferences.Editor editor = preferences.edit();
        if (currentMarker != null) {
            editor.putString(CURRENT_MARKER_KEY, SimpleMarker.simpleMarkerToJson(currentMarker));
        } else {
            editor.remove(CURRENT_MARKER_KEY);
        }
        editor.apply();
    }
}
