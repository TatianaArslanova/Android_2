package com.example.ama.android2_lesson01.widget

import android.content.Intent
import android.widget.RemoteViewsService

class ListNoteService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
            ListNoteRemoteViewsFactory(applicationContext)
}