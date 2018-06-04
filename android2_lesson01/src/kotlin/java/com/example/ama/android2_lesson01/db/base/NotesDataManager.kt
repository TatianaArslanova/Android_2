package com.example.ama.android2_lesson01.db.base

import com.example.ama.android2_lesson01.model.Note

/**
 * Interface describes methods for managing notes
 */

interface NotesDataManager {
    fun loadListOfAllNotes(callback: LoadDataCallback)
    fun createNote(title: String, text: String, callback: DataChangedCallback)
    fun updateNote(targetNote: Note, newTitle: String, newText: String, callback: DataChangedCallback)
    fun deleteNote(targetNote: Note, callback: DataChangedCallback)

    interface LoadDataCallback {
        fun onLoad(mData: ArrayList<Note>)
    }

    interface DataChangedCallback {
        fun onDataChanged()
    }
}