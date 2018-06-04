package com.example.ama.android2_lesson01

import android.app.Application
import com.example.ama.android2_lesson01.db.NotesProviderDataManager
import com.example.ama.android2_lesson01.db.base.NotesDataManager

class NotesApp : Application() {

    companion object {
        lateinit var instance: NotesApp
        lateinit var dataManager: NotesDataManager
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        dataManager = NotesProviderDataManager(applicationContext)
    }
}