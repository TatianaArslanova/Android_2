package com.example.ama.android2_lesson03.ui.search.base;

import com.example.ama.android2_lesson03.base.Presenter;

public interface SearchPresenter<T extends SearchOnTheMapView> extends Presenter<T> {
    void findAddress(String query);

    void sendUserInput(String query);
}
