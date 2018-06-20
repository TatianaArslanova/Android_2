package com.example.ama.android2_lesson03.repo.data.model;

import com.example.ama.android2_lesson03.repo.data.state.SearchOnTheMapStateSaver;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Model with basic marker parameters for saving to favorites
 */
public class SimpleMarker {
    private static final String MARKER_TITLE_KEY = "title";
    private static final String MARKER_ADDRESS_KEY = "address";
    private static final String MARKER_LATITUDE_KEY = "latitude";
    private static final String MARKER_LONGITUDE_KEY = "longitude";

    private String title;
    private String address;
    private LatLng position;

    public SimpleMarker(String address, LatLng position) {
        this.address = address;
        this.position = position;
        title = address;
    }

    public SimpleMarker(String title, String address, LatLng position) {
        this.title = title;
        this.address = address;
        this.position = position;
    }

    public static SimpleMarker getFromMarker(Marker marker) {
        if (marker != null) {
            if (marker.getSnippet() != null) {
                return new SimpleMarker(marker.getTitle(), marker.getSnippet(), marker.getPosition());
            }
            return new SimpleMarker(marker.getTitle(), marker.getPosition());
        }
        return null;
    }

    public static SimpleMarker simpleMarkerFromJson(String json) {
        if (json == null || json.equals(SearchOnTheMapStateSaver.NOT_FOUND)) return null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            return new SimpleMarker(
                    jsonObject.getString(MARKER_TITLE_KEY),
                    jsonObject.getString(MARKER_ADDRESS_KEY),
                    new LatLng(jsonObject.getDouble(MARKER_LATITUDE_KEY), jsonObject.getDouble(MARKER_LONGITUDE_KEY)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String simpleMarkerToJson(SimpleMarker marker) {
        if (marker == null) return null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(MARKER_TITLE_KEY, marker.getTitle());
            jsonObject.put(MARKER_ADDRESS_KEY, marker.getAddress());
            jsonObject.put(MARKER_LATITUDE_KEY, marker.getPosition().latitude);
            jsonObject.put(MARKER_LONGITUDE_KEY, marker.getPosition().longitude);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SimpleMarker) {
            SimpleMarker other = (SimpleMarker) obj;
            return address.equals(other.getAddress()) && position.equals(other.getPosition());
        }
        return false;
    }

    public int hashCode() {
        return (this.address != null ? this.address.hashCode() : 0) * 31 + (this.position != null ? this.position.hashCode() : 0);
    }

    public LatLng getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
