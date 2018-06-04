package com.example.ama.android2_lesson01.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.example.ama.android2_lesson01.db.base.BaseProviderDataManager;
import com.example.ama.android2_lesson01.db.base.NotesDataManager;
import com.example.ama.android2_lesson01.model.Note;

import java.util.ArrayList;

import static com.example.ama.android2_lesson01.db.provider.NotesDatabaseContract.NotesTable;

/**
 * Class for managing notes on the database
 */

public class NotesProviderDataManager extends BaseProviderDataManager implements NotesDataManager {

    public NotesProviderDataManager(Context context) {
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

    @Override
    public void loadListOfAllNotes(LoadDataCallback callback) {
        ArrayList<Note> listOfNotes = new ArrayList<>();
        Cursor allNotes = null;
        try {
            allNotes = query();
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

    @Override
    public void createNote(String title, String text, DataChangedCallback callback) {
        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_NAME_TITLE, title);
        values.put(NotesTable.COLUMN_NAME_TEXT, text);
        insert(values);
        callback.onDataChanged();
    }

    /**
     * Remove the row from database table described {@link NotesTable}
     * corresponding to the given {@link Note} by it's id on the database
     * and notify changes by callback
     *
     * @param targetNote the {@link Note} to remove
     * @param callback   callback for notify changes
     * @see NotesTable
     * @see Note
     */

    @Override
    public void deleteNote(Note targetNote, DataChangedCallback callback) {
        delete(targetNote.getmId());
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

    @Override
    public void updateNote(Note targetNote, String newTitle, String newText, DataChangedCallback callback) {
        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_NAME_TITLE, newTitle);
        values.put(NotesTable.COLUMN_NAME_TEXT, newText);
        update(targetNote.getmId(), values);
        callback.onDataChanged();
    }
}
