package com.example.ama.android2_lesson03.repo.data.model

import com.example.ama.android2_lesson03.repo.data.state.NOT_FOUND
import com.example.ama.android2_lesson03.repo.data.state.SearchOnTheMapStateSaver
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import org.json.JSONException
import org.json.JSONObject

/**
 * Model with basic marker parameters for saving to favorites
 */
data class SimpleMarker(val address: String, val position: LatLng, var title: String = address) {

    companion object {
        private const val MARKER_TITLE_KEY = "title"
        private const val MARKER_ADDRESS_KEY = "address"
        private const val MARKER_LATITUDE_KEY = "latitude"
        private const val MARKER_LONGITUDE_KEY = "longitude"

        fun getFromMarker(marker: Marker?): SimpleMarker? =
                if (marker != null) {
                    if (marker.snippet != null) {
                        SimpleMarker(marker.snippet, marker.position, marker.title)
                    } else {
                        SimpleMarker(marker.title, marker.position)
                    }
                } else null

        fun simpleMarkerToJson(marker: SimpleMarker): String? {
            val jsonObject = JSONObject()
            try {
                jsonObject.put(MARKER_TITLE_KEY, marker.title)
                jsonObject.put(MARKER_ADDRESS_KEY, marker.address)
                jsonObject.put(MARKER_LATITUDE_KEY, marker.position.latitude)
                jsonObject.put(MARKER_LONGITUDE_KEY, marker.position.longitude)
                return jsonObject.toString()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return null
        }

        fun simpleMarkerFromJson(json: String): SimpleMarker? {
            if (json!= NOT_FOUND) {
                try {
                    val jsonObject = JSONObject(json)
                    return SimpleMarker(
                            jsonObject.getString(MARKER_ADDRESS_KEY),
                            LatLng(jsonObject.getDouble(MARKER_LATITUDE_KEY), jsonObject.getDouble(MARKER_LONGITUDE_KEY)),
                            jsonObject.getString(MARKER_TITLE_KEY))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            return null
        }
    }

    override fun toString() = title

    override fun equals(other: Any?) =
            if (other is SimpleMarker) this.address == other.address && this.position == other.position else false

    override fun hashCode(): Int {
        return (if (this.address != null) this.address.hashCode() else 0) * 31 + if (this.position != null) this.position.hashCode() else 0
    }
}