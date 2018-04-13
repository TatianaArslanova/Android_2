package com.example.ama.android2_lesson01.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.ama.android2_lesson01.model.Note;

import java.util.ArrayList;

import static com.example.ama.android2_lesson01.db.NotesDatabaseContract.NotesTable;

public class NotesDataManager extends NotesDbHelper {

    public NotesDataManager(Context context) {
        super(context);
    }

    public ArrayList<Note> getListOfAllNotes() {
        ArrayList<Note> listOfNotes = new ArrayList<>();
        Cursor allNotes = getAllNotesCursor(NotesTable.TABLE_NAME);
        if (allNotes.moveToFirst()) {
            int titleColomn = allNotes.getColumnIndex(NotesTable.COLOMN_NAME_TITLE);
            int textColomn = allNotes.getColumnIndex(NotesTable.COLOMN_NAME_TEXT);
            do {
                listOfNotes.add(Note.builder()
                        .title(allNotes.getString(titleColomn))
                        .text(allNotes.getString(textColomn))
                        .build());
            } while (allNotes.moveToNext());
        }
        return listOfNotes;
    }

    public Note createNote(String name, String text) {
        Note newNote=Note.builder()
                .title(name)
                .text(text)
                .build();
        addNoteToDatabase(newNote);
        return newNote;
    }

    public boolean removeNote(Note note) {
        //TODO: remove note
        return false;
    }

    public boolean updateNote(Note note, String name, String text) {
        //TODO: update note
        return false;
    }

    private void addNoteToDatabase(Note note) {
        ContentValues values=new ContentValues();
        values.put(NotesTable.COLOMN_NAME_TITLE, note.getTitle());
        values.put(NotesTable.COLOMN_NAME_TEXT, note.getText());
        insertValues(NotesTable.TABLE_NAME, values);
    }
}
