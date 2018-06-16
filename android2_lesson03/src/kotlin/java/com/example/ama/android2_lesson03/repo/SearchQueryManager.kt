package com.example.ama.android2_lesson03.repo

import com.example.ama.android2_lesson03.repo.base.QueryManager
import com.google.android.gms.maps.model.LatLng

class SearchQueryManager : QueryManager {

    override fun prepareGMapUri(query: String) {
    }

    override fun prepareGMapUri(latLng: LatLng) {
    }

    override fun getPreparedUri(callback: QueryManager.OnUriPreparedCallback) {
    }

    override fun getFullLocationName(query: String, callback: QueryManager.OnFullNamePreparedCallback) {
    }

    override fun getFullLocationName(latLng: LatLng, callback: QueryManager.OnFullNamePreparedCallback) {
    }

    override fun getMyLocation(callback: QueryManager.OnLocationSearchResultCallback) {
    }
}