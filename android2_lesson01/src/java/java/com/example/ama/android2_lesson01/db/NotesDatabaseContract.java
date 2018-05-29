package com.example.ama.android2_lesson01.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract class with constants for database
 */

public final class NotesDatabaseContract {
    public NotesDatabaseContract() {
    }

    public static final String AND = " AND ";
    public static final String AUTHORITY = "com.example.ama.android2_lesson01.provider";
    public static final String PATH_NOTES = "notes";
    public static final String PATH_ONE_NOTE = PATH_NOTES + "/#";
    public static final String NOTE_CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + PATH_NOTES;
    public static final String NOTE_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + PATH_NOTES;
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH_NOTES);
    public static final Uri CONTENT_URI_ITEM = Uri.parse("content://" + AUTHORITY + "/" + PATH_ONE_NOTE);
    public static final int NOTES = 1;
    public static final int NOTES_ID = 2;


    public static abstract class NotesTable implements BaseColumns {
        public static final String TABLE_NAME = "textnotes";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME_TITLE + " TEXT, " +
                        COLUMN_NAME_TEXT + " TEXT)";
        public static final String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String BASE_ID_SELECTION = _ID + " = ";
        public static final String SQL_WHERE_ID_ARG_MASK = BASE_ID_SELECTION + "?";


    }

}
