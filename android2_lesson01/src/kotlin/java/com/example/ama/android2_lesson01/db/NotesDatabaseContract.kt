package com.example.ama.android2_lesson01.db

import android.provider.BaseColumns

object NotesTable {
    const val TABLE_NAME = "textnotes"
    const val COLOMN_NAME_ID = "_id"
    const val COLOMN_NAME_TITLE = "title"
    const val COLOMN_NAME_TEXT = "text"
    const val SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLOMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLOMN_NAME_TITLE + " TEXT, " +
                    COLOMN_NAME_TEXT + " TEXT)"
    const val SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME
    const val SQL_WHERE_ID = COLOMN_NAME_ID + " =?"
}