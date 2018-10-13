package com.example.ama.android2_lesson06

import android.app.Application

class SmsExampleApp : Application() {

    companion object {
        lateinit var instance: SmsExampleApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}