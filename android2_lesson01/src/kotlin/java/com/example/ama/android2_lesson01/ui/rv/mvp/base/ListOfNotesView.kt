package com.example.ama.android2_lesson01.ui.rv.mvp.base

import com.example.ama.android2_lesson01.model.Note
import com.example.ama.android2_lesson01.ui.base.NoteView

/**
 * Interface for work with [ListPresenter]
 */

interface ListOfNotesView : NoteView {
    fun showNoteList(mData: ArrayList<Note>)
    fun showEmptyMessage()
    fun hideEmptyMessage()
}