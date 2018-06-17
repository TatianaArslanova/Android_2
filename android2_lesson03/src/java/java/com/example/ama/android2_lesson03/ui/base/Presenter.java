package com.example.ama.android2_lesson03.ui.base;

/**
 * Base interface for presenters
 * @param <T> view for work with
 */
public interface Presenter<T extends PocketMapView> {
    void attachView(T view);

    void detachView();

}
