package com.example.ama.android2_lesson01.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.ama.android2_lesson01.db.NotesDatabaseContract.NotesTable;

/**
 * Class for for working with the database
 */

public class NotesDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 5;

    public NotesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Calls the first time when the database created.
     *
     * @param db the database
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NotesTable.SQL_CREATE_TABLE);
    }

    /** Calls when database version updated
     *
     * @param db the database
     * @param oldVersion old version of database
     * @param newVersion new version of database
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(NotesTable.SQL_DROP_TABLE);
        this.onCreate(db);
    }

    public Cursor getAllNotesCursor(String tableName) {
        return getReadableDatabase().query(tableName, null, null, null, null, null, null);
    }

    public void insertRow(String tableName, ContentValues values) {
        getWritableDatabase().insert(tableName, null, values);
    }

    public void deleteRow(String tableName, String whereSql, String[] args) {
        getWritableDatabase().delete(tableName, whereSql, args);
    }

    public void updateRow(String tableName, ContentValues values, String whereSql, String[] args) {
        getWritableDatabase().update(tableName, values, whereSql, args);
    }
}
