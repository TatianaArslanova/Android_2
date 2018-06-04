package com.example.ama.android2_lesson01.ui.rv.mvp.base;

import com.example.ama.android2_lesson01.model.Note;
import com.example.ama.android2_lesson01.ui.base.NoteView;
import com.example.ama.android2_lesson01.ui.rv.mvp.ListOfNotesPresenter;

import java.util.ArrayList;

/**
 * Interface for work with {@link ListPresenter}
 */
public interface ListOfNotesView extends NoteView {
    void showNoteList(ArrayList<Note> mData);

    void showEmptyMessage();

    void hideEmptyMessage();
}
