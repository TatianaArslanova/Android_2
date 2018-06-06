package com.example.ama.android2_lesson03.base;

public interface Presenter<T extends SearchAddressView> {
    void attachView(T view);

    void detachView();

    void findAddress(String query);

    void sendToGMap(String query);

}
