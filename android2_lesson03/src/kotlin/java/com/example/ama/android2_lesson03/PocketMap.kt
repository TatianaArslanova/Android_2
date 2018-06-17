package com.example.ama.android2_lesson03

import android.app.Application
import com.example.ama.android2_lesson03.repo.SearchQueryManager
import com.example.ama.android2_lesson03.repo.base.QueryManager

class PocketMap : Application() {

    companion object {
        lateinit var instance: PocketMap
        lateinit var queryManager: QueryManager
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        queryManager = SearchQueryManager()
    }
}