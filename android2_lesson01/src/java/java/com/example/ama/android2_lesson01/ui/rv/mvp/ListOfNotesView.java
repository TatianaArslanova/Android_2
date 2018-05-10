package com.example.ama.android2_lesson01.ui.rv.mvp;

import com.example.ama.android2_lesson01.ui.base.NoteView;
import com.example.ama.android2_lesson01.model.Note;

import java.util.ArrayList;

/**
 * Interface for work with {@link ListOfNotesPresenter}
 */
public interface ListOfNotesView extends NoteView {
    void showNoteList(ArrayList<Note> mData);

    void showEmptyMessage();

    void hideEmptyMessage();
}
