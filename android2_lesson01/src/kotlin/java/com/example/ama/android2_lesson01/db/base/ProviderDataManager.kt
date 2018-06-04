package com.example.ama.android2_lesson01.db.base

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

/**
 * Interface describes methods for work with ContentProvider
 */

interface ProviderDataManager {
    fun query(): Cursor
    fun insert(values: ContentValues): Uri
    fun update(id: Long, values: ContentValues): Int
    fun delete(id: Long): Int
}