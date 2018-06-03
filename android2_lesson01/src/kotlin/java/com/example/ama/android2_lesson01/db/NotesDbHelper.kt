package com.example.ama.android2_lesson01.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Class for for working with the database
 */

class NotesDbHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "notes.db"
        const val DATABASE_VERSION = 3
    }

    /**
     * Calls the first time when the database created.
     *
     * @param db the database
     */

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(NotesTable.SQL_CREATE_TABLE)
    }

    /** Calls when database version updated
     *
     * @param db the database
     * @param oldVersion old version of database
     * @param newVersion new version of database
     */

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(NotesTable.SQL_DROP_TABLE)
        onCreate(db)
    }

    /**
     * Get entries from the table of the database
     *
     * @param tableName     name of the table
     * @param selection     where condition for sql query
     * @param selectionArgs arguments for where condition
     * @return [Cursor] object that contains entries from the table
     */

    fun getNotesCursor(tableName: String, selection: String?, selectionArgs: Array<out String>?): Cursor =
            readableDatabase.query(tableName, null, selection, selectionArgs, null, null, null)

    /**
     * Insert new row to the table of the database
     *
     * @param tableName name of the table
     * @param values    values to insert
     */

    fun insertRow(tableName: String, values: ContentValues?): Long =
            writableDatabase.insert(tableName, null, values)

    /**
     * Delete the row from the table of the database
     *
     * @param tableName name of the table
     * @param whereSql  where condition for sql query
     * @param args      arguments for where condition
     */

    fun deleteRow(tableName: String, whereSql: String?, args: Array<out String>?): Int =
            writableDatabase.delete(tableName, whereSql, args)

    /**
     * Update the row from the table on the database
     *
     * @param tableName name of the table
     * @param values    values to update
     * @param whereSql  where condition for sql query
     * @param args      arguments for where condition
     */

    fun updateRow(tableName: String, values: ContentValues?, whereSql: String?, args: Array<out String>?): Int =
            writableDatabase.update(tableName, values, whereSql, args)
}