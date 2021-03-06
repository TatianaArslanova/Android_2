package com.example.ama.android2_lesson01.ui.rv.mvp;

import com.example.ama.android2_lesson01.NotesApp;
import com.example.ama.android2_lesson01.model.Note;
import com.example.ama.android2_lesson01.ui.base.BasePresenter;
import com.example.ama.android2_lesson01.ui.rv.mvp.base.ListOfNotesView;
import com.example.ama.android2_lesson01.ui.rv.mvp.base.ListPresenter;

/**
 * Presenter implementation for {@link ListOfNotesView}
 *
 * @param <T> view for which to work
 */

public class ListOfNotesPresenter<T extends ListOfNotesView>
        extends BasePresenter<T>
        implements ListPresenter<T> {

    /**
     * Request data from the database and transfer it to the view
     */

    @Override
    public void loadData() {
        NotesApp.getDataManager().loadListOfAllNotes(mData -> {
            view.showNoteList(mData);
            if (mData.size() == 0) {
                view.showEmptyMessage();
            } else {
                view.hideEmptyMessage();
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
        NotesApp.getDataManager().deleteNote(note, () -> loadData());
    }
}
