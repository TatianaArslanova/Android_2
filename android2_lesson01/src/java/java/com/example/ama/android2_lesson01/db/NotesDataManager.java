package com.example.ama.android2_lesson01.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.example.ama.android2_lesson01.model.Note;

import java.util.ArrayList;

import static com.example.ama.android2_lesson01.db.NotesDatabaseContract.NotesTable;

/**
 * Class for managing notes on the database
 */

public class NotesDataManager extends NotesDbHelper {

    public NotesDataManager(Context context) {
        super(context);
    }

    /**
     * Create ArrayList with all data from database table described {@link NotesTable}
     * and send it by callback. Converts every row to {@link Note} object
     *
     * @param callback callback for sending ArrayList
     * @see NotesTable
     * @see Note
     */

    public void getListOfAllNotes(LoadDataCallback callback) {
        ArrayList<Note> listOfNotes = new ArrayList<>();
        Cursor allNotes = getAllNotesCursor(NotesTable.TABLE_NAME);
        try {
            if (allNotes.moveToFirst()) {
                int idColomn = allNotes.getColumnIndex(NotesTable._ID);
                int titleColomn = allNotes.getColumnIndex(NotesTable.COLUMN_NAME_TITLE);
                int textColomn = allNotes.getColumnIndex(NotesTable.COLUMN_NAME_TEXT);
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
        callback.onLoad(listOfNotes);
    }

    /**
     * Add new row to database table described {@link NotesTable}
     * and notify changes by callback
     *
     * @param title    content for title column
     * @param text     content for text column
     * @param callback callback for notify changes
     * @see NotesTable
     */

    public void createNote(String title, String text, DataChangedCallback callback) {
        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_NAME_TITLE, title);
        values.put(NotesTable.COLUMN_NAME_TEXT, text);
        insertRow(NotesTable.TABLE_NAME, values);
        callback.onDataChanged();
    }

    /**
     * Remove the row from database table described {@link NotesTable}
     * corresponding to the given {@link Note} by it's id on the database
     * and notify changes by callback
     *
     * @param note     the {@link Note} to remove
     * @param callback callback for notify changes
     * @see NotesTable
     * @see Note
     */

    public void removeNote(Note note, DataChangedCallback callback) {
        String[] args = {String.valueOf(note.getmId())};
        deleteRow(NotesTable.TABLE_NAME, NotesTable.SQL_WHERE_ID, args);
        callback.onDataChanged();
    }

    /**
     * Update the row from database table described {@link NotesTable}
     * corresponding to the given {@link Note} by it's id on the database
     * with new column values and notify changes by callback
     *
     * @param targetNote the {@link Note} to update
     * @param newTitle   new title for title column
     * @param newText    new text for text column
     * @param callback   callback for notify changes
     * @see NotesTable
     * @see Note
     */

    public void updateNote(Note targetNote, String newTitle, String newText, DataChangedCallback callback) {
        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_NAME_TITLE, newTitle);
        values.put(NotesTable.COLUMN_NAME_TEXT, newText);
        String[] args = {String.valueOf(targetNote.getmId())};
        updateRow(NotesTable.TABLE_NAME, values, NotesTable.SQL_WHERE_ID, args);
        callback.onDataChanged();
    }

    /**
     * Interface for loading data callback
     */

    public interface LoadDataCallback {
        void onLoad(ArrayList<Note> mData);
    }

    /**
     * Interface for changing data callback
     */
    public interface DataChangedCallback {
        void onDataChanged();
    }
}
