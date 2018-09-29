package com.example.ama.android2_lesson01.ui.details.mvp;

import com.example.ama.android2_lesson01.NotesApp;
import com.example.ama.android2_lesson01.db.base.NotesDataManager;
import com.example.ama.android2_lesson01.model.Note;
import com.example.ama.android2_lesson01.ui.base.BasePresenter;
import com.example.ama.android2_lesson01.ui.details.mvp.base.DetailsNoteView;
import com.example.ama.android2_lesson01.ui.details.mvp.base.DetailsPresenter;

/**
 * Presenter implementation for {@link DetailsNoteView}
 *
 * @param <T> view for which to work
 */

public class DetailsNotePresenter<T extends DetailsNoteView>
        extends BasePresenter<T>
        implements DetailsPresenter<T> {

    /**
     * Send data for note creation on the database and notify view about changes
     *
     * @param title title for the note to create
     * @param text  text for the note to create
     * @see NotesDataManager
     */

    @Override
    public void createNote(String title, String text) {
        NotesApp.getDataManager().createNote(title, text, () -> view.finishEditing());
    }

    /**
     * Send data for note updating on the database and notify view about changes
     *
     * @param targetNote note for updating
     * @param newTitle   new title for the note
     * @param newText    new text for the note
     * @see NotesDataManager
     * @see Note
     */

    @Override
    public void updateNote(Note targetNote, String newTitle, String newText) {
        NotesApp.getDataManager().updateNote(targetNote, newTitle, newText, () -> view.finishEditing());
    }

    /**
     * Send data for note deleting from the database and notify view about changes
     *
     * @param note note to delete
     * @see NotesDataManager
     * @see Note
     */

    @Override
    public void deleteNote(Note note) {
        if (note != null) {
            NotesApp.getDataManager().deleteNote(note, () -> view.finishEditing());
        } else {
            view.finishEditing();
        }
    }
}
