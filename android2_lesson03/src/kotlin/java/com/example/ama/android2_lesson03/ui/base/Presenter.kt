package com.example.ama.android2_lesson03.ui.base

/**
 * Base interface for presenters
 * @param <T> view for work with
 */
interface Presenter<T : PocketMapView> {
    fun attachView(view: T)
    fun detachView()
}