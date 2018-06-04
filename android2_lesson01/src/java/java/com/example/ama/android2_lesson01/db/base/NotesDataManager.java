package com.example.ama.android2_lesson01.db.base;

import com.example.ama.android2_lesson01.model.Note;

import java.util.ArrayList;

/**
 * Interface describes methods for managing notes
 */

public interface NotesDataManager {
    void loadListOfAllNotes(LoadDataCallback callback);

    void createNote(String title, String text, DataChangedCallback callback);

    void updateNote(Note targetNote, String newTitle, String newText, DataChangedCallback callback);

    void deleteNote(Note targetNote, DataChangedCallback callback);

    interface LoadDataCallback {
        void onLoad(ArrayList<Note> mData);
    }

    interface DataChangedCallback {
        void onDataChanged();
    }
}
