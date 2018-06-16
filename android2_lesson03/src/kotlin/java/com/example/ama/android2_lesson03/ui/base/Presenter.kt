package com.example.ama.android2_lesson03.ui.base

interface Presenter<T : PocketMapView> {
    fun attachView(view: T)
    fun detachView()
}