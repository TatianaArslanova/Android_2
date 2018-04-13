package com.example.ama.android2_lesson01.db;

import android.provider.BaseColumns;

public final class NotesDatabaseContract {
    public NotesDatabaseContract() {
    }

    public static abstract class NotesTable implements BaseColumns {
        public static final String TABLE_NAME = "textnotes";
        public static final String COLOMN_NAME_TITILE = "title";
        public static final String COLOMN_NAME_TEXT = "text";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE" + TABLE_NAME + "{" +
                        _ID + "INTEGER PRIMARY_KEY AUTOINCREMENT," +
                        COLOMN_NAME_TITILE + "TEXT," +
                        COLOMN_NAME_TEXT + "TEXT)";
        public static final String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

}
