package com.example.ama.android2_lesson03.ui.base;

/**
 * Base implementation for {@link Presenter}
 *
 * @param <T> view for work with
 */
abstract public class BasePresenter<T extends PocketMapView> implements Presenter<T> {
    protected T view;

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
