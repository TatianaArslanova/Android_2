package com.example.ama.android2_lesson01.db;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

import com.example.ama.android2_lesson01.model.Note;

import java.util.ArrayList;

import static com.example.ama.android2_lesson01.db.NotesDatabaseContract.CONTENT_URI_ITEM;
import static com.example.ama.android2_lesson01.db.NotesDatabaseContract.NotesTable;

/**
 * Class for managing notes on the database
 */

public class NotesDataManager {

    private Context context;

    public NotesDataManager(Context context) {
        this.context = context;
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
        Cursor allNotes = null;
        try {
            allNotes = context.getContentResolver()
                    .query(NotesDatabaseContract.CONTENT_URI, null, null, null, null);
            if (allNotes != null) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (allNotes != null && !allNotes.isClosed()) {
                allNotes.close();
            }
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
        context.getContentResolver().insert(NotesDatabaseContract.CONTENT_URI, values);
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
        Uri uriForNote = ContentUris.withAppendedId(NotesDatabaseContract.CONTENT_URI_ITEM, note.getmId());
        context.getContentResolver().delete(uriForNote, null, null);
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
        Uri uriForNote = ContentUris.withAppendedId(CONTENT_URI_ITEM, targetNote.getmId());
        context.getContentResolver().update(uriForNote, values, null, null);
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
