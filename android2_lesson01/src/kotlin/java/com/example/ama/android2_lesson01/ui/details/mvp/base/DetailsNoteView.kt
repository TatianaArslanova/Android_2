package com.example.ama.android2_lesson01.ui.details.mvp.base

import com.example.ama.android2_lesson01.ui.base.NoteView

/**
 * Interface for work with [DetailsPresenter]
 */

interface DetailsNoteView : NoteView {
    fun finishEditing()
}