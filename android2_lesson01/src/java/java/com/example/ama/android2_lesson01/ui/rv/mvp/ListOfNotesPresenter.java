package com.example.ama.android2_lesson01.ui.rv.mvp;

import com.example.ama.android2_lesson01.NotesApp;
import com.example.ama.android2_lesson01.ui.base.BasePresenter;
import com.example.ama.android2_lesson01.db.NotesDataManager;
import com.example.ama.android2_lesson01.model.Note;

import java.util.ArrayList;

/**
 * Presenter implementation for {@link ListOfNotesView}
 *
 * @param <T> view for which to work
 */

public class ListOfNotesPresenter<T extends ListOfNotesView> extends BasePresenter<T> {

    /**
     * Request data from the database and transfer it to the view
     */

    @Override
    public void loadData() {
        NotesApp.getDataManager().getListOfAllNotes(new NotesDataManager.LoadDataCallback() {
            @Override
            public void onLoad(ArrayList<Note> mData) {
                view.showNoteList(mData);
                if (mData.size() == 0) {
                    view.showEmptyMessage();
                } else {
                    view.hideEmptyMessage();
                }
            }
        });
    }

    /**
     * Send data for note deleting from the database and notify view about changes
     *
     * @param note note to delete
     */

    @Override
    public void deleteNote(Note note) {
        NotesApp.getDataManager().removeNote(note, new NotesDataManager.DataChangedCallback() {
            @Override
            public void onDataChanged() {
                loadData();
            }
        });
    }
}
