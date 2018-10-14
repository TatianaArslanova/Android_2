package com.example.ama.android2_lesson06.ui.mvp

import android.database.Cursor
import com.example.ama.android2_lesson06.ui.base.BaseSmsPresenter
import com.example.ama.android2_lesson06.ui.base.SmsExpampleView
import io.reactivex.android.schedulers.AndroidSchedulers

class SmsExamplePresenter<T : SmsExpampleView> : BaseSmsPresenter<T>() {

    override fun exportMessages(cursor: Cursor?) {
        disposable?.add(manager.startExport(cursor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { message -> view?.showMessage(message) })
    }

    override fun importMessages() {
        disposable?.add(manager.startImport()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { message -> view?.showMessage(message) })
    }
}