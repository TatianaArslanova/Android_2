package com.example.ama.android2_lesson01.ui.details.mvp;

import com.example.ama.android2_lesson01.ui.base.NoteView;

/**
 * Interface for work with {@link DetailsNotePresenter}
 */

public interface DetailsNoteView extends NoteView {
    void finishEditing();
}
