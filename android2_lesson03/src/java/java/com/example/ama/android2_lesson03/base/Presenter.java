package com.example.ama.android2_lesson03.base;

public interface Presenter<T extends PocketMapView> {
    void attachView(T view);

    void detachView();

}
