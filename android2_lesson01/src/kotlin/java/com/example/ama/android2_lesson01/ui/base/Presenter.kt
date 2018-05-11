package com.example.ama.android2_lesson01.ui.base

import com.example.ama.android2_lesson01.model.Note

/**
 * Base interface for Presenter
 *
 * @param <T> view with which to work
 */

interface Presenter<T : NoteView> {
    fun attachView(view: T)
    fun detachView()
    fun loadData()
    fun createNote(title: String, text: String)
    fun deleteNote(note: Note)
    fun updateNote(targetNote: Note, newTitle: String, newText: String)
}