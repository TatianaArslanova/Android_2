package com.example.ama.android2_lesson03.repo

import android.location.Address
import android.net.Uri
import com.example.ama.android2_lesson03.repo.base.QueryManager
import com.example.ama.android2_lesson03.repo.data.LocationManagerAndroid
import com.example.ama.android2_lesson03.repo.data.UriManager
import com.example.ama.android2_lesson03.repo.data.base.LocManager
import com.google.android.gms.maps.model.LatLng
import java.lang.StringBuilder

class SearchQueryManager : QueryManager {

    companion object {
        const val DEFAULT_ZOOM: Float = 15f
    }

    private val locManager: LocManager = LocationManagerAndroid()
    private val uriManager = UriManager()

    override fun getPreparedUri(isMarkerOnTheMap: Boolean, cameraPosition: LatLng?, zoom: Float?, callback: (uri: Uri) -> Unit) {
        if (!isMarkerOnTheMap) {
            uriManager.prepareUriByCameraPosition(cameraPosition, zoom)
        }
        callback.invoke(uriManager.preparedUri)
    }

    override fun getMyLocation(found: (latLng: LatLng, zoom: Float) -> Unit,
                               notFound: (message: String) -> Unit,
                               permissionRequired: (permission: String, requestCode: Int) -> Unit) {
        locManager.findMyLocation(found, notFound, permissionRequired)
    }

    override fun getFullLocationName(query: String, callback: (fullLocationName: String, latLng: LatLng, zoom: Float) -> Unit) {
        val address = locManager.findAddressByQuery(query)
        if (address != null) {
            uriManager.prepareUriByQuery(query)
            callback.invoke(
                    buildFullName(address),
                    LatLng(address.latitude, address.longitude),
                    DEFAULT_ZOOM)

        }
    }

    override fun getFullLocationName(latLng: LatLng, callback: (fullLocationName: String, latLng: LatLng, zoom: Float) -> Unit) {
        val address = locManager.findAddressByLatLng(latLng)
        if (address != null) {
            val fullLocationName = buildFullName(address)
            uriManager.prepareUriByMarkerLocation(latLng, fullLocationName)
            callback.invoke(
                    fullLocationName,
                    latLng,
                    DEFAULT_ZOOM)
        }
    }

    private fun buildFullName(address: Address): String {
        val builder = StringBuilder()
        for (i in 0..address.maxAddressLineIndex) {
            if (builder.isNotEmpty()) builder.append(",")
            builder.append(address.getAddressLine(i))
        }
        return builder.toString()
    }
}