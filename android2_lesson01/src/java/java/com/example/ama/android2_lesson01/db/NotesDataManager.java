package com.example.ama.android2_lesson01.db;

import android.content.Context;

import com.example.ama.android2_lesson01.model.Note;

import java.util.ArrayList;

public class NotesDataManager extends NotesDbHelper {

    public NotesDataManager(Context context) {
        super(context);
    }

    public ArrayList<Note> getListOfAllNotes() {
        //TODO: get data from db
        return null;
    }

    public boolean createNote(String name, String text) {
        //TODO: build note
        return false;
    }

    public boolean removeNote(Note note) {
        //TODO: remove note
        return false;
    }

    public boolean updateNote(Note note, String name, String text) {
        //TODO: update note
        return false;
    }

    private boolean addNoteToDatabase(Note note) {
        //TODO: add note to database
        return false;
    }
}
