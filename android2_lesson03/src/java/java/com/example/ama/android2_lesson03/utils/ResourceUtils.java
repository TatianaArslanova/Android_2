package com.example.ama.android2_lesson03.utils;

import android.location.Location;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.R;

public class ResourceUtils {

    public static String formatLocationCoordinates(Location location) {
        return String.format(PocketMap.getInstance().getResources().getString(R.string.widget_latlng_format),
                location.getLatitude(), location.getLongitude());
    }

    public static String getStringResource(int resId) {
        return PocketMap.getInstance().getResources().getString(resId);
    }
}
