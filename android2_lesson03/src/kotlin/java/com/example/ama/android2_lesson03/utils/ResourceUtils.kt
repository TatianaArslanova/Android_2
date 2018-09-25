package com.example.ama.android2_lesson03.utils

import android.location.Location
import com.example.ama.android2_lesson03.PocketMap
import com.example.ama.android2_lesson03.R

object ResourceUtils {

    fun formatLocationCoordinates(location: Location) =
            String.format(PocketMap.instance.resources.getString(R.string.widget_latlng_format),
                    location.latitude, location.longitude)
}