package com.example.ama.android2_lesson03

import android.app.Application

class PocketMap : Application() {

    companion object {
        lateinit var instance: PocketMap
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}