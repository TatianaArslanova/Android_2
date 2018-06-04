package com.example.ama.android2_lesson01.ui.rv.mvp

import com.example.ama.android2_lesson01.NotesApp
import com.example.ama.android2_lesson01.db.base.NotesDataManager
import com.example.ama.android2_lesson01.model.Note
import com.example.ama.android2_lesson01.ui.base.BasePresenter
import com.example.ama.android2_lesson01.ui.rv.mvp.base.ListOfNotesView
import com.example.ama.android2_lesson01.ui.rv.mvp.base.ListPresenter

/**
 * Presenter implementation for [ListOfNotesView]
 *
 * @param <T> view for which to work
 */

class ListOfNotesPresenter<T : ListOfNotesView> :
        BasePresenter<T>(),
        ListPresenter<T> {

    /**
     * Request data from the database and transfer it to the view
     */

    override fun loadData() {
        NotesApp.dataManager.loadListOfAllNotes(object : NotesDataManager.LoadDataCallback {
            override fun onLoad(mData: ArrayList<Note>) {
                view?.showNoteList(mData)
                if (mData.size != 0) {
                    view?.hideEmptyMessage()
                } else {
                    view?.showEmptyMessage()
                }
            }
        })
    }

    /**
     * Send data for note deleting from the database and notify view about changes
     *
     * @param note note to delete
     */

    override fun deleteNote(note: Note?) {
        if (note != null) {
            NotesApp.dataManager.deleteNote(note, object : NotesDataManager.DataChangedCallback {
                override fun onDataChanged() {
                    loadData()
                }
            })
        }
    }
}