package com.example.ama.android2_lesson03.repo.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.repo.data.base.MarkerManager;
import com.example.ama.android2_lesson03.repo.model.SimpleMarker;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for managing favorite markers, working with SharedPreferences
 */
public class PreferencesMarkerManager implements MarkerManager {
    private static final String PREFERENCES_FILE_NAME = "marker_preferences";
    private static final String MARKERS_KEY = "all_markers";

    private static final String MARKER_TITLE_KEY = "title";
    private static final String MARKER_LATITUDE_KEY = "latitude";
    private static final String MARKER_LONGITUDE_KEY = "longitude";

    private SharedPreferences preferences;

    public PreferencesMarkerManager() {
        preferences = PocketMap.getInstance().getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public ArrayList<SimpleMarker> getAllMarkers() {
        Set<String> markerSet = getFromPreferences();
        ArrayList<SimpleMarker> markers = new ArrayList<>();
        for (String o : markerSet) {
            markers.add(simpleMarkerFromJson(o));
        }
        return markers;
    }

    @Override
    public void addMarker(SimpleMarker marker) {
        Set<String> markerSet = getFromPreferences();
        markerSet.add(simpleMarkerToJson(marker));
        putInPreferences(markerSet);
    }

    @Override
    public void updateMarker(SimpleMarker marker, String newName) {
        Set<String> markerSet = getFromPreferences();
        markerSet.remove(simpleMarkerToJson(marker));
        marker.setTitle(newName);
        markerSet.add(simpleMarkerToJson(marker));
        putInPreferences(markerSet);
    }

    @Override
    public void deleteMarker(SimpleMarker marker) {
        Set<String> markerSet = getFromPreferences();
        markerSet.remove(simpleMarkerToJson(marker));
        putInPreferences(markerSet);
    }

    private void putInPreferences(Set<String> markerSet) {
        preferences.edit().clear().putStringSet(MARKERS_KEY, markerSet).apply();
    }

    private Set<String> getFromPreferences() {
        return preferences.getStringSet(MARKERS_KEY, new HashSet<String>());
    }


    private SimpleMarker simpleMarkerFromJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return new SimpleMarker(
                    jsonObject.getString(MARKER_TITLE_KEY),
                    new LatLng(jsonObject.getDouble(MARKER_LATITUDE_KEY), jsonObject.getDouble(MARKER_LONGITUDE_KEY)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    private String simpleMarkerToJson(SimpleMarker marker) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(MARKER_TITLE_KEY, marker.getTitle());
            jsonObject.put(MARKER_LATITUDE_KEY, marker.getPosition().latitude);
            jsonObject.put(MARKER_LONGITUDE_KEY, marker.getPosition().longitude);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
