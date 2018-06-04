package com.example.ama.android2_lesson01.db.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Provider for database
 */

public class NotesProvider extends ContentProvider {

    private static final String EXCEPTION_MESSAGE = "Wrong Uri: ";

    private static final UriMatcher uriMatcher;
    private NotesDbHelper notesDb;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(NotesDatabaseContract.AUTHORITY, NotesDatabaseContract.PATH_NOTES, NotesDatabaseContract.NOTES);
        uriMatcher.addURI(NotesDatabaseContract.AUTHORITY, NotesDatabaseContract.PATH_ONE_NOTE, NotesDatabaseContract.NOTES_ID);
    }

    @Override
    public boolean onCreate() {
        notesDb = new NotesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case NotesDatabaseContract.NOTES_ID:
                selection = clarifySelectionWithId(selection, uri);
                break;
            case NotesDatabaseContract.NOTES:
                break;
            default:
                throw new IllegalArgumentException(EXCEPTION_MESSAGE + uri.toString());
        }
        Cursor cursor = notesDb.getNotesCursor(
                NotesDatabaseContract.NotesTable.TABLE_NAME,
                selection,
                selectionArgs);
        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case NotesDatabaseContract.NOTES:
                return NotesDatabaseContract.NOTE_CONTENT_TYPE;
            case NotesDatabaseContract.NOTES_ID:
                return NotesDatabaseContract.NOTE_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (uriMatcher.match(uri) == NotesDatabaseContract.NOTES) {
            long id = notesDb.insertRow(NotesDatabaseContract.NotesTable.TABLE_NAME, values);
            if (id > 0) {
                Uri itemUri = ContentUris.withAppendedId(NotesDatabaseContract.CONTENT_URI, id);
                notifyChangeCursor(itemUri);
                return itemUri;
            }
        } else {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE + uri.toString());
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case NotesDatabaseContract.NOTES_ID:
                selection = clarifySelectionWithId(selection, uri);
                break;
            case NotesDatabaseContract.NOTES:
                break;
            default:
                throw new IllegalArgumentException(EXCEPTION_MESSAGE + uri.toString());
        }
        int count = notesDb.deleteRow(NotesDatabaseContract.NotesTable.TABLE_NAME, selection, selectionArgs);
        notifyChangeCursor(uri);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case NotesDatabaseContract.NOTES_ID:
                selection = clarifySelectionWithId(selection, uri);
                break;
            case NotesDatabaseContract.NOTES:
                break;
            default:
                throw new IllegalArgumentException(EXCEPTION_MESSAGE + uri.toString());
        }
        int count = notesDb.updateRow(NotesDatabaseContract.NotesTable.TABLE_NAME, values, selection, selectionArgs);
        notifyChangeCursor(uri);
        return count;
    }

    private void notifyChangeCursor(Uri uri) {
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }

    private String clarifySelectionWithId(String selection, Uri uri) {
        String id = uri.getLastPathSegment();
        if (TextUtils.isEmpty(selection)) {
            selection = NotesDatabaseContract.NotesTable.BASE_ID_SELECTION + id;
        } else {
            selection = selection + NotesDatabaseContract.AND + NotesDatabaseContract.NotesTable.BASE_ID_SELECTION + id;
        }
        return selection;
    }
}
