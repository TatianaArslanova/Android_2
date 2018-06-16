package com.example.ama.android2_lesson03.ui.base

abstract class BasePresenter<T : PocketMapView> : Presenter<T> {

    protected var view: T? = null

    override fun attachView(view: T) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}