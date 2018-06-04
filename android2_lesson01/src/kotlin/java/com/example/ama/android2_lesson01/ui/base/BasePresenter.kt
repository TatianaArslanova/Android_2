package com.example.ama.android2_lesson01.ui.base

import com.example.ama.android2_lesson01.model.Note

/**
 * Base implementation of [Presenter]
 *
 * @param <T> view with which to work
 */

abstract class BasePresenter<T : NoteView> : Presenter<T> {
    protected var view: T? = null

    /**
     * Attach view with which to work
     *
     * @param view for attaching
     */

    override fun attachView(view: T) {
        this.view = view
    }

    /**
     * Detach using view
     */

    override fun detachView() {
        view = null
    }
}