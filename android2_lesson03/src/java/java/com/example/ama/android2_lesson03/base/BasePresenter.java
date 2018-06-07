package com.example.ama.android2_lesson03.base;

import com.example.ama.android2_lesson03.model.UserQueryManager;
import com.example.ama.android2_lesson03.model.base.QueryManager;

abstract public class BasePresenter<T extends PocketMapView> implements Presenter<T> {
    protected T view;
    protected QueryManager queryManager;

    public BasePresenter() {
        queryManager = new UserQueryManager();
    }

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
