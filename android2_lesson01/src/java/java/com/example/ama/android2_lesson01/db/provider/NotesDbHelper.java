package com.example.ama.android2_lesson01.db.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.ama.android2_lesson01.db.provider.NotesDatabaseContract.NotesTable;

/**
 * Class for for working with the database
 */

public class NotesDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 6;

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

    /**
     * Calls when database version updated
     *
     * @param db         the database
     * @param oldVersion old version of database
     * @param newVersion new version of database
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(NotesTable.SQL_DROP_TABLE);
        this.onCreate(db);
    }

    /**
     * Get entries from the table of the database
     *
     * @param tableName     name of the table
     * @param selection     where condition for sql query
     * @param selectionArgs arguments for where condition
     * @return {@link Cursor} object that contains all entries from the table
     */

    public Cursor getNotesCursor(String tableName, String selection, String[] selectionArgs) {
        return getReadableDatabase().query(tableName, null, selection, selectionArgs, null, null, null);
    }

    /**
     * Insert new row to the table of the database
     *
     * @param tableName name of the table
     * @param values    values to insert
     */

    public long insertRow(String tableName, ContentValues values) {
        return getWritableDatabase().insert(tableName, null, values);
    }

    /**
     * Delete the row from the table of the database
     *
     * @param tableName name of the table
     * @param whereSql  where condition for sql query
     * @param args      arguments for where condition
     */

    public int deleteRow(String tableName, String whereSql, String[] args) {
        return getWritableDatabase().delete(tableName, whereSql, args);
    }

    /**
     * Update the row from the table on the database
     *
     * @param tableName name of the table
     * @param values    values to update
     * @param whereSql  where condition for sql query
     * @param args      arguments for where condition
     */

    public int updateRow(String tableName, ContentValues values, String whereSql, String[] args) {
        return getWritableDatabase().update(tableName, values, whereSql, args);
    }
}
