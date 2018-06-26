package com.example.ama.android2_lesson03.repo.data.markers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.repo.data.base.MarkerManager;
import com.example.ama.android2_lesson03.repo.data.model.SimpleMarker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for managing favorite markers, working with SharedPreferences
 */
public class PreferencesMarkerManager implements MarkerManager {
    private static final String PREFERENCES_FILE_NAME = "marker_preferences";
    private static final String MARKERS_KEY = "all_markers";

    private SharedPreferences preferences;

    public PreferencesMarkerManager() {
        preferences = PocketMap.getInstance().getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public ArrayList<SimpleMarker> getAllMarkers() {
        Set<String> markerSet = getFromPreferences();
        ArrayList<SimpleMarker> markers = new ArrayList<>();
        for (String o : markerSet) {
            SimpleMarker marker = SimpleMarker.simpleMarkerFromJson(o);
            if (marker != null) {
                markers.add(marker);
            }
        }
        return markers;
    }

    @Override
    public void addMarker(SimpleMarker marker) {
        Set<String> markerSet = getFromPreferences();
        markerSet.add(SimpleMarker.simpleMarkerToJson(marker));
        putInPreferences(markerSet);
    }

    @Override
    public void updateMarker(SimpleMarker marker, String newName) {
        Set<String> markerSet = getFromPreferences();
        markerSet.remove(SimpleMarker.simpleMarkerToJson(marker));
        marker.setTitle(newName);
        markerSet.add(SimpleMarker.simpleMarkerToJson(marker));
        putInPreferences(markerSet);
    }

    @Override
    public void deleteMarker(SimpleMarker marker) {
        Set<String> markerSet = getFromPreferences();
        markerSet.remove(SimpleMarker.simpleMarkerToJson(marker));
        putInPreferences(markerSet);
    }

    @Override
    public boolean isMarkerExists(SimpleMarker marker) {
        return getAllMarkers().contains(marker);
    }

    private void putInPreferences(Set<String> markerSet) {
        preferences.edit().clear().putStringSet(MARKERS_KEY, markerSet).apply();
    }

    private Set<String> getFromPreferences() {
        return preferences.getStringSet(MARKERS_KEY, new HashSet<String>());
    }
}
