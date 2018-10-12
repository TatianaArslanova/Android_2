package com.example.ama.android2_lesson06.ui.base;

import com.example.ama.android2_lesson06.repo.SmsStorageManager;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseSmsPresenter<T extends SmsExampleView> implements SmsPresenter<T> {

    protected T view;
    protected SmsStorageManager manager;
    protected CompositeDisposable disposable;

    @Override
    public void attachView(T view) {
        this.view = view;
        if (disposable == null) {
            disposable = new CompositeDisposable();
        }
        if (manager == null) {
            manager = new SmsStorageManager();
        }
    }

    @Override
    public void detachView() {
        view = null;
        if (disposable != null) {
            disposable.clear();
        }
    }
}
