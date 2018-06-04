package com.example.ama.android2_lesson01.ui.details.mvp.base;

import com.example.ama.android2_lesson01.model.Note;
import com.example.ama.android2_lesson01.ui.base.Presenter;

public interface DetailsPresenter<T extends DetailsNoteView> extends Presenter<T> {
    void createNote(String title, String text);

    void updateNote(Note targetNote, String newTitle, String newText);
}
