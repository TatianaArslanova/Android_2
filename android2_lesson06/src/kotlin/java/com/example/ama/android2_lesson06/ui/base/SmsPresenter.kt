package com.example.ama.android2_lesson06.ui.base

import android.database.Cursor

interface SmsPresenter<T : SmsExpampleView> {
    fun attachView(view: T)
    fun detachView()
    fun exportMessages(cursor: Cursor?)
    fun importMessages()
}