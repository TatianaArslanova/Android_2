package com.example.ama.android2_lesson01.ui.details.mvp

import com.example.ama.android2_lesson01.NotesApp
import com.example.ama.android2_lesson01.db.NotesProviderDataManager
import com.example.ama.android2_lesson01.db.base.NotesDataManager
import com.example.ama.android2_lesson01.model.Note
import com.example.ama.android2_lesson01.ui.base.BasePresenter
import com.example.ama.android2_lesson01.ui.details.mvp.base.DetailsNoteView
import com.example.ama.android2_lesson01.ui.details.mvp.base.DetailsPresenter

/**
 * Presenter implementation for [DetailsNoteView]
 *
 * @param <T> view for which to work
 */

class DetailsNotePresenter<T : DetailsNoteView> :
        BasePresenter<T>(),
        DetailsPresenter<T> {

    /**
     * Send data for note creation on the database and notify view about changes
     *
     * @param title title for the note to create
     * @param text  text for the note to create
     * @see NotesProviderDataManager
     */

    override fun createNote(title: String, text: String) {
        NotesApp.dataManager.createNote(title, text,
                object : NotesDataManager.DataChangedCallback {
                    override fun onDataChanged() {
                        view?.finishEditing()
                    }
                })
    }

    /**
     * Send data for note deleting from the database and notify view about changes
     *
     * @param note note to delete
     * @see NotesProviderDataManager
     * @see Note
     */

    override fun deleteNote(note: Note?) {
        if (note != null) {
            NotesApp.dataManager.deleteNote(note,
                    object : NotesDataManager.DataChangedCallback {
                        override fun onDataChanged() {
                            view?.finishEditing()
                        }
                    })
        } else {
            view?.finishEditing()
        }
    }

    /**
     * Send data for note updating on the database and notify view about changes
     *
     * @param targetNote note for updating
     * @param newTitle   new title for the note
     * @param newText    new text for the note
     * @see NotesProviderDataManager
     * @see Note
     */

    override fun updateNote(targetNote: Note, newTitle: String, newText: String) {
        NotesApp.dataManager.updateNote(targetNote, newTitle, newText,
                object : NotesDataManager.DataChangedCallback {
                    override fun onDataChanged() {
                        view?.finishEditing()
                    }
                })
    }
}