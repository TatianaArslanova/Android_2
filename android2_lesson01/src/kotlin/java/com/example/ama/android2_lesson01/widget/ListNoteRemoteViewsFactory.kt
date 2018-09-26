package com.example.ama.android2_lesson01.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.ama.android2_lesson01.NotesApp
import com.example.ama.android2_lesson01.R
import com.example.ama.android2_lesson01.db.base.NotesDataManager
import com.example.ama.android2_lesson01.model.Note
import com.example.ama.android2_lesson01.ui.details.DetailsNoteFragment

class ListNoteRemoteViewsFactory(val context: Context)
    : RemoteViewsService.RemoteViewsFactory {

    val notes: ArrayList<Note> = ArrayList()

    override fun onCreate() {
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onDataSetChanged() {
        NotesApp.dataManager.loadListOfAllNotes(object : NotesDataManager.LoadDataCallback {
            override fun onLoad(mData: ArrayList<Note>) {
                notes.clear()
                notes.addAll(mData)
            }
        })
    }

    override fun hasStableIds() = true

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_item_view)
        remoteViews.setTextViewText(R.id.tv_card_note_title, notes[position].title)
        remoteViews.setTextViewText(R.id.tv_card_note_text, notes[position].text)

        val intent = Intent()
        intent.putExtra(DetailsNoteFragment.TARGET_NOTE, notes[position])
        remoteViews.setOnClickFillInIntent(R.id.ll_widget_item, intent)
        return remoteViews
    }

    override fun getCount() = notes.size

    override fun getViewTypeCount() = 1

    override fun onDestroy() {
        notes.clear()
    }
}