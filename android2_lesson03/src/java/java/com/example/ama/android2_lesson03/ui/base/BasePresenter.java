package com.example.ama.android2_lesson03.ui.base;

import com.example.ama.android2_lesson03.repo.SearchQueryManager;
import com.example.ama.android2_lesson03.repo.base.QueryManager;

abstract public class BasePresenter<T extends PocketMapView> implements Presenter<T> {
    protected T view;
    protected QueryManager queryManager;

    public BasePresenter() {
        queryManager = new SearchQueryManager();
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
