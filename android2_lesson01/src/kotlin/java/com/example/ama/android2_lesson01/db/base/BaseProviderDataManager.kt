package com.example.ama.android2_lesson01.db.base

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.example.ama.android2_lesson01.db.provider.NotesProvider

/**
 * Base implementation for [ProviderDataManager]
 * for work with [NotesProvider]
 */

abstract class BaseProviderDataManager(protected val context: Context) : ProviderDataManager {

    override fun query(): Cursor =
            context.contentResolver
                    .query(NotesProvider.CONTENT_URI, null, null, null, null)


    override fun insert(values: ContentValues): Uri =
            context.contentResolver.insert(NotesProvider.CONTENT_URI, values)

    override fun update(id: Long, values: ContentValues): Int {
        val uri = ContentUris.withAppendedId(NotesProvider.CONTENT_URI_ITEM, id)
        return context.contentResolver.update(uri, values, null, null)
    }

    override fun delete(id: Long): Int {
        val uri = ContentUris.withAppendedId(NotesProvider.CONTENT_URI_ITEM, id)
        return context.contentResolver.delete(uri, null, null)
    }
}