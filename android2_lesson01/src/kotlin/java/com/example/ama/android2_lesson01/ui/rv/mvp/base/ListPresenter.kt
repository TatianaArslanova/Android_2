package com.example.ama.android2_lesson01.ui.rv.mvp.base

import com.example.ama.android2_lesson01.ui.base.Presenter

interface ListPresenter<T : ListOfNotesView> : Presenter<T> {
    fun loadData()
}