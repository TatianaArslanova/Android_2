package com.example.ama.android2_lesson03.ui.base

/**
 * Base implementation for [Presenter]
 *
 * @param <T> view for work with
 */
abstract class BasePresenter<T : PocketMapView> : Presenter<T> {

    protected var view: T? = null

    override fun attachView(view: T) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}