package com.example.ama.android2_lesson03.repo.data;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

/**
 * Class for preparing Uri for sending to another apps
 */
public class UriManager {
    private static final String URI_GEO_SHEME = "geo:";
    private static final String BASE_GEO_QUERY = "geo:0,0?q=";
    private static final String ZOOM_PARAMETER = "?z=";

    private Uri preparedUri = Uri.parse(BASE_GEO_QUERY);

    public void prepareUriByMarkerLocation(LatLng latLng, String label) {
        preparedUri = Uri.parse(BASE_GEO_QUERY + latLng.latitude + "," + latLng.longitude + "{" + label + ")");
    }

    public void prepareUriByQuery(String query) {
        preparedUri = Uri.parse(BASE_GEO_QUERY + query);
    }

    public void prepareUriByCameraPosition(LatLng latLng, float zoom) {
        preparedUri = Uri.parse(URI_GEO_SHEME + latLng.latitude + "," + latLng.longitude + ZOOM_PARAMETER + zoom);
    }

    public Uri getPreparedUri() {
        return preparedUri;
    }
}
