package com.example.ama.android2_lesson01.db.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.text.TextUtils

/**
 * Provider for database
 */

class NotesProvider : ContentProvider() {

    companion object Contract {
        const val EXCEPTION_MESSAGE = "Wrong Uri: "
        const val AND = " AND "
        const val AUTHORITY = "com.example.ama.android2_lesson01.provider"
        const val PATH_NOTES = "notes"
        const val PATH_ONE_NOTE = "notes/#"
        const val NOTE_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$AUTHORITY.$PATH_NOTES"
        const val NOTE_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$AUTHORITY.$PATH_NOTES"
        const val NOTES = 1
        const val NOTES_ID = 2

        val CONTENT_URI = Uri.parse("content://$AUTHORITY/$PATH_NOTES")!!
        val CONTENT_URI_ITEM = Uri.parse("content://$AUTHORITY/$PATH_ONE_NOTE")!!
    }

    private lateinit var notesDb: NotesDbHelper
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        uriMatcher.addURI(AUTHORITY, PATH_NOTES, NOTES)
        uriMatcher.addURI(AUTHORITY, PATH_ONE_NOTE, NOTES_ID)
    }

    override fun onCreate(): Boolean {
        notesDb = NotesDbHelper(context)
        return true
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        val resultCursor = notesDb.getNotesCursor(
                NotesTable.TABLE_NAME,
                when (uriMatcher.match(uri)) {
                    NOTES_ID -> clarifySelectionWithId(selection, uri)
                    NOTES -> selection
                    else -> throw IllegalArgumentException(EXCEPTION_MESSAGE + uri.toString())
                },
                selectionArgs)
        resultCursor.setNotificationUri(context.contentResolver, uri)
        return resultCursor
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        if (uriMatcher.match(uri) == NOTES) {
            val id = notesDb.insertRow(NotesTable.TABLE_NAME, values)
            if (id > 0) {
                val itemUri = ContentUris.withAppendedId(CONTENT_URI, id)
                context.contentResolver.notifyChange(itemUri, null)
                return itemUri
            }
        } else {
            throw IllegalArgumentException(EXCEPTION_MESSAGE + uri.toString())
        }
        return null
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        val count = notesDb.deleteRow(
                NotesTable.TABLE_NAME,
                when (uriMatcher.match(uri)) {
                    NOTES_ID -> clarifySelectionWithId(selection, uri)
                    NOTES -> selection
                    else -> throw IllegalArgumentException(EXCEPTION_MESSAGE + uri.toString())
                },
                selectionArgs)
        context.contentResolver.notifyChange(uri, null)
        return count
    }


    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val count = notesDb.updateRow(
                NotesTable.TABLE_NAME,
                values,
                when (uriMatcher.match(uri)) {
                    NOTES_ID -> clarifySelectionWithId(selection, uri)
                    NOTES -> selection
                    else -> throw IllegalArgumentException(EXCEPTION_MESSAGE + uri.toString())
                },
                selectionArgs)
        context.contentResolver.notifyChange(uri, null)
        return count
    }

    override
    fun getType(uri: Uri?): String? =
            when (uriMatcher.match(uri)) {
                NOTES_ID -> NOTE_CONTENT_ITEM_TYPE
                NOTES -> NOTE_CONTENT_TYPE
                else -> null
            }

    private fun clarifySelectionWithId(selection: String?, uri: Uri?): String =
            if (TextUtils.isEmpty(selection)) {
                NotesTable.BASE_ID_SELECTION + uri?.lastPathSegment
            } else {
                selection + AND + NotesTable.BASE_ID_SELECTION + uri?.lastPathSegment
            }
}