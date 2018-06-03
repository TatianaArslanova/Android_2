package com.example.ama.android2_lesson01.db

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import com.example.ama.android2_lesson01.model.Note

/**
 * Class for managing notes
 */

class NotesDataManager(private val context: Context) {

    /**
     * Create ArrayList with all data from database table described [NotesTable]
     * and send it by callback. Converts every row to [Note] object
     *
     * @param callback callback for sending ArrayList
     * @see NotesTable
     * @see Note
     */

    fun getListOfAllNotes(callback: LoadDataCallback) {
        val listOfNotes = ArrayList<Note>()
        var allNotes: Cursor? = null
        try {
            allNotes = context.contentResolver.query(NotesProvider.CONTENT_URI, null, null, null, null)
            if (allNotes.moveToFirst()) {
                val idColomn = allNotes.getColumnIndex(NotesTable.COLUMN_NAME_ID)
                val titleColomn = allNotes.getColumnIndex(NotesTable.COLUMN_NAME_TITLE)
                val textColomn = allNotes.getColumnIndex(NotesTable.COLUMN_NAME_TEXT)
                do {
                    listOfNotes.add(Note.builder()
                            .id(allNotes.getLong(idColomn))
                            .title(allNotes.getString(titleColomn))
                            .text(allNotes.getString(textColomn))
                            .build())
                } while (allNotes.moveToNext())
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            if (allNotes != null && !allNotes.isClosed) {
                allNotes.close()
            }
        }
        callback.onLoad(listOfNotes)
    }

    /**
     * Add new row to database table described [NotesTable]
     * and notify changes by callback
     *
     * @param title title of new note
     * @param text  text of new note
     * @param callback callback for notify changes
     * @see NotesTable
     */

    fun createNote(title: String, text: String, callback: DataChangedCallback) {
        val values = ContentValues()
        values.put(NotesTable.COLUMN_NAME_TITLE, title)
        values.put(NotesTable.COLUMN_NAME_TEXT, text)
        context.contentResolver.insert(NotesProvider.CONTENT_URI, values)
        callback.onDataChanged()
    }

    /**
     * Remove the row from database table described [NotesTable]
     * corresponding to the given [Note] by it's id on the database
     * and notify changes by callback
     *
     * @param note the [Note] to remove
     * @param callback callback for notify changes
     * @see NotesTable
     * @see Note
     */

    fun removeNote(note: Note, callback: DataChangedCallback) {
        val uriForNote = ContentUris.withAppendedId(NotesProvider.CONTENT_URI_ITEM, note.id)
        context.contentResolver.delete(uriForNote, null, null)
        callback.onDataChanged()
    }

    /**
     * Update the row from database table described [NotesTable]
     * corresponding to the given [Note] by it's id on the database
     * with new column values and notify changes by callback
     *
     * @param note     the [Note] to update
     * @param newTitle new title for given note
     * @param newText  new text for given note
     * @param callback callback for notify changes
     * @see NotesTable
     * @see Note
     */

    fun updateNote(note: Note, newTitle: String, newText: String, callback: DataChangedCallback) {
        val values = ContentValues()
        values.put(NotesTable.COLUMN_NAME_TITLE, newTitle)
        values.put(NotesTable.COLUMN_NAME_TEXT, newText)
        val uriForNote = ContentUris.withAppendedId(NotesProvider.CONTENT_URI_ITEM, note.id)
        context.contentResolver.update(uriForNote, values, null, null)
        callback.onDataChanged()
    }

    /**
     * Interface for loading data callback
     */

    interface LoadDataCallback {
        fun onLoad(mData: ArrayList<Note>)
    }

    /**
     * Interface for changing data callback
     */

    interface DataChangedCallback {
        fun onDataChanged()
    }
}