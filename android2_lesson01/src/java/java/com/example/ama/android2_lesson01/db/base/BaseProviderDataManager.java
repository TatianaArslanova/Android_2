package com.example.ama.android2_lesson01.db.base;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.ama.android2_lesson01.db.provider.NotesDatabaseContract;

/**
 * Base implementation for {@link ProviderDataManager}
 * for work with {@link com.example.ama.android2_lesson01.db.provider.NotesProvider}
 */

public abstract class BaseProviderDataManager implements ProviderDataManager {

    protected Context context;

    public BaseProviderDataManager(Context context) {
        this.context = context;
    }

    @Override
    public Cursor query() {
        return context.getContentResolver()
                .query(NotesDatabaseContract.CONTENT_URI, null, null, null, null);
    }

    @Override
    public Uri insert(ContentValues values) {
        return context.getContentResolver().insert(NotesDatabaseContract.CONTENT_URI, values);
    }

    @Override
    public int update(long id, ContentValues values) {
        Uri uri = ContentUris.withAppendedId(NotesDatabaseContract.CONTENT_URI_ITEM, id);
        return context.getContentResolver().update(uri, values, null, null);
    }

    @Override
    public int delete(long id) {
        Uri uri = ContentUris.withAppendedId(NotesDatabaseContract.CONTENT_URI_ITEM, id);
        return context.getContentResolver().delete(uri, null, null);
    }
}
