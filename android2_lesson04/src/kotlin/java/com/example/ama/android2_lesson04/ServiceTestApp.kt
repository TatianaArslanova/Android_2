package com.example.ama.android2_lesson04

import android.app.Application
import com.example.ama.android2_lesson04.data.Data

class ServiceTestApp : Application() {

    companion object {
        lateinit var instance: ServiceTestApp
        lateinit var data: Data
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        data = Data()
    }
}