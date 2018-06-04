package com.example.ama.android2_lesson01.ui.rv.mvp.base;

import com.example.ama.android2_lesson01.ui.base.Presenter;

public interface ListPresenter<T extends ListOfNotesView> extends Presenter<T> {
    void loadData();
}
