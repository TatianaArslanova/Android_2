package com.example.ama.android2_lesson01.db

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import com.example.ama.android2_lesson01.model.Note

/**
 * Class for managing notes
 */

class NotesDataManager(context: Context) : NotesDbHelper(context) {

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
        val allNotes = getAllNotesCursor(NotesTable.TABLE_NAME)
        try {
            if (allNotes.moveToFirst()) {
                val idColomn = allNotes.getColumnIndex(NotesTable.COLOMN_NAME_ID)
                val titleColomn = allNotes.getColumnIndex(NotesTable.COLOMN_NAME_TITLE)
                val textColomn = allNotes.getColumnIndex(NotesTable.COLOMN_NAME_TEXT)
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
            allNotes.close()
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
        values.put(NotesTable.COLOMN_NAME_TITLE, title)
        values.put(NotesTable.COLOMN_NAME_TEXT, text)
        insertRow(NotesTable.TABLE_NAME, values)
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
        val args = arrayOf(note.id.toString())
        deleteRow(NotesTable.TABLE_NAME, NotesTable.SQL_WHERE_ID, args)
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
        values.put(NotesTable.COLOMN_NAME_TITLE, newTitle)
        values.put(NotesTable.COLOMN_NAME_TEXT, newText)
        val args = arrayOf(note.id.toString())
        updateRow(NotesTable.TABLE_NAME, values, NotesTable.SQL_WHERE_ID, args)
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