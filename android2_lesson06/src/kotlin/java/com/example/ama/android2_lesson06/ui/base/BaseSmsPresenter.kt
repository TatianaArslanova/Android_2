package com.example.ama.android2_lesson06.ui.base

import com.example.ama.android2_lesson06.repo.SmsStorageManager
import io.reactivex.disposables.CompositeDisposable

abstract class BaseSmsPresenter<T : SmsExpampleView> : SmsPresenter<T> {

    protected var view: T? = null
    protected var manager: SmsStorageManager = SmsStorageManager()
    protected var disposable: CompositeDisposable? = null

    override fun attachView(view: T) {
        this.view = view
        if (disposable == null) {
            disposable = CompositeDisposable()
        }
    }

    override fun detachView() {
        view = null
        disposable?.clear()
    }
}