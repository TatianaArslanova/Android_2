package com.example.ama.android2_lesson01.db

import android.content.Context
import com.example.ama.android2_lesson01.model.Note

class NotesDataManager(context: Context) : NotesDbHelper(context) {

    fun getListOfAllNotes(): ArrayList<Note> = ArrayList()
    fun createNote(title: String, text: String) {
        //TODO: create note
    }

    fun removeNote(note: Note) {
        //TODO: remove note
    }

    fun updateNote(note: Note, newTitle: String, newText: String) {
        //TODO: update note
    }
}