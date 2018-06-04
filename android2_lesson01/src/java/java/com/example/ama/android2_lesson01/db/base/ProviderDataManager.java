package com.example.ama.android2_lesson01.db.base;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Interface describes methods for work with ContentProvider
 */

public interface ProviderDataManager {
    Cursor query();

    Uri insert(ContentValues values);

    int update(long id, ContentValues values);

    int delete(long id);
}
