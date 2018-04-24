package com.example.ama.android2_lesson01.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.example.ama.android2_lesson01.model.Note;

import java.util.ArrayList;

import static com.example.ama.android2_lesson01.db.NotesDatabaseContract.NotesTable;

/**
 * Class for managing notes
 */

public class NotesDataManager extends NotesDbHelper {

    public NotesDataManager(Context context) {
        super(context);
    }

    /**
     * Get all data from database table described {@link NotesTable}
     * converts every row to {@link Note} object
     *
     * @return ArrayList that contains all existing notes from database
     * @see NotesTable
     * @see Note
     */

    public ArrayList<Note> getListOfAllNotes() {
        ArrayList<Note> listOfNotes = new ArrayList<>();
        Cursor allNotes = getAllNotesCursor(NotesTable.TABLE_NAME);
        try {
            if (allNotes.moveToFirst()) {
                int idColomn = allNotes.getColumnIndex(NotesTable._ID);
                int titleColomn = allNotes.getColumnIndex(NotesTable.COLOMN_NAME_TITLE);
                int textColomn = allNotes.getColumnIndex(NotesTable.COLOMN_NAME_TEXT);
                do {
                    listOfNotes.add(Note.builder()
                            .id(allNotes.getLong(idColomn))
                            .title(allNotes.getString(titleColomn))
                            .text(allNotes.getString(textColomn))
                            .build());
                } while (allNotes.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            allNotes.close();
        }
        return listOfNotes;
    }

    /**
     * Add new row to database table described {@link NotesTable}
     *
     * @param title title of new note
     * @param text  text of new note
     * @see NotesTable
     */

    public void createNote(String title, String text) {
        ContentValues values = new ContentValues();
        values.put(NotesTable.COLOMN_NAME_TITLE, title);
        values.put(NotesTable.COLOMN_NAME_TEXT, text);
        insertRow(NotesTable.TABLE_NAME, values);
    }

    /**
     * Remove the row from database table described {@link NotesTable}
     * corresponding to the given {@link Note}
     *
     * @param note the {@link Note} to remove
     * @see NotesTable
     * @see Note
     */

    public void removeNote(Note note) {
        String[] args = {String.valueOf(note.getmId())};
        deleteRow(NotesTable.TABLE_NAME, NotesTable.SQL_WHERE_ID, args);
    }

    /**
     * Update the row from database table described {@link NotesTable}
     * corresponding to the given {@link Note} with new params
     *
     * @param note     the {@link Note} to update
     * @param newTitle new title for given note
     * @param newText  new text for given note
     * @see NotesTable
     * @see Note
     */

    public void updateNote(Note note, String newTitle, String newText) {
        ContentValues values = new ContentValues();
        values.put(NotesTable.COLOMN_NAME_TITLE, newTitle);
        values.put(NotesTable.COLOMN_NAME_TEXT, newText);
        String[] args = {String.valueOf(note.getmId())};
        updateRow(NotesTable.TABLE_NAME, values, NotesTable.SQL_WHERE_ID, args);
    }
}
