package com.example.ama.android2_lesson01.ui.base;

import com.example.ama.android2_lesson01.model.Note;

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

    /**
     * Do nothing. Needs to override for specific implementation
     */

    @Override
    public void loadData() {
    }

    /**
     * Do nothing. Needs to override for specific implementation
     */

    @Override
    public void createNote(String title, String text) {
    }

    /**
     * Do nothing. Needs to override for specific implementation
     */

    @Override
    public void deleteNote(Note note) {
    }

    /**
     * Do nothing. Needs to override for specific implementation
     */

    @Override
    public void updateNote(Note targetNote, String newTitle, String newText) {
    }
}
