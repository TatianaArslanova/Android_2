package com.example.ama.android2_lesson03.utils

import com.example.ama.android2_lesson03.PocketMap
import com.example.ama.android2_lesson03.R
import com.google.android.gms.maps.model.LatLng

object ResourceUtils {

    fun formatLatLngCoordinates(latLng: LatLng) =
            String.format(PocketMap.instance.resources.getString(R.string.widget_latlng_format),
                    latLng.latitude, latLng.longitude)
}