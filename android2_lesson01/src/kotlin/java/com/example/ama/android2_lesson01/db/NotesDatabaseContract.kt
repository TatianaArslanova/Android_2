package com.example.ama.android2_lesson01.db

/**
 * Object that contains constants for table
 * textnotes in database
 */

object NotesTable {
    const val TABLE_NAME = "textnotes"
    const val COLUMN_NAME_ID = "_id"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_TEXT = "text"
    const val SQL_CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ($COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_NAME_TITLE TEXT, $COLUMN_NAME_TEXT TEXT)"
    const val SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS $TABLE_NAME"
    const val BASE_ID_SELECTION = "$COLUMN_NAME_ID ="
    const val SQL_WHERE_ID_ARG_MASK = "$BASE_ID_SELECTION?"
}