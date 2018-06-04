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

    fun deleteNote(note: Note?)

}