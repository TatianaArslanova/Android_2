package com.example.ama.android2_lesson01.ui.base;

import com.example.ama.android2_lesson01.model.Note;

/**
 * Base interface for Presenter
 *
 * @param <T> view with which to work
 */

public interface Presenter<T extends NoteView> {

    void attachView(T view);

    void detachView();

    void deleteNote(Note note);


}
