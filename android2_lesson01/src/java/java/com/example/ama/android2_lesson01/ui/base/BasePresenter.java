package com.example.ama.android2_lesson01.ui.base;

/**
 * Base implementation of {@link Presenter}
 *
 * @param <T> view with which to work
 */

abstract public class BasePresenter<T extends NoteView> implements Presenter<T> {
    protected T view;

    /**
     * Attach view with which to work
     *
     * @param view for attaching
     */

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    /**
     * Detach using view
     */

    @Override
    public void detachView() {
        view = null;
    }

}
