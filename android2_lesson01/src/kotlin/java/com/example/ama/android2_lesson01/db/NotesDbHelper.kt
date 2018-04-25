package com.example.ama.android2_lesson01.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class NotesDbHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "notes.db"
        const val DATABASE_VERSION = 2
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(NotesTable.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(NotesTable.SQL_DROP_TABLE)
        onCreate(db)
    }

    fun getAllNotesCursor(tableName: String): Cursor =
            readableDatabase.query(tableName, null, null, null, null, null, null)

    fun insertRow(tableName: String, values: ContentValues) {
        writableDatabase.insert(tableName, null, values)
    }
}