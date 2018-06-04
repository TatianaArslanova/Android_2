package com.example.ama.android2_lesson01.ui.details.mvp.base

import com.example.ama.android2_lesson01.model.Note
import com.example.ama.android2_lesson01.ui.base.Presenter

interface DetailsPresenter<T : DetailsNoteView> : Presenter<T> {
    fun createNote(title: String, text: String)
    fun updateNote(targetNote: Note, newTitle: String, newText: String)
}