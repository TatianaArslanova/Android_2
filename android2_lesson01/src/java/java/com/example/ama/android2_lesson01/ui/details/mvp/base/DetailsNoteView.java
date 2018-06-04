package com.example.ama.android2_lesson01.ui.details.mvp.base;

import com.example.ama.android2_lesson01.ui.base.NoteView;
import com.example.ama.android2_lesson01.ui.details.mvp.DetailsNotePresenter;

/**
 * Interface for work with {@link DetailsPresenter}
 */

public interface DetailsNoteView extends NoteView {
    void finishEditing();
}
