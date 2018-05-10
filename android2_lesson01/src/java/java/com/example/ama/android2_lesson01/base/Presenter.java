package com.example.ama.android2_lesson01.base;

import com.example.ama.android2_lesson01.model.Note;

/**
 * Base interface for Presenter
 *
 * @param <T> view with which to work
 */

public interface Presenter<T extends NoteView> {

    void attachView(T view);

    void detachView();

    void loadData();

    void createNote(String title, String text);

    void deleteNote(Note note);

    void updateNote(Note targetNote, String newTitle, String newText);
}
