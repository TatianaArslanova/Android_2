package com.example.ama.android2_lesson01.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import com.example.ama.android2_lesson01.db.base.BaseProviderDataManager
import com.example.ama.android2_lesson01.db.base.NotesDataManager
import com.example.ama.android2_lesson01.db.provider.NotesTable
import com.example.ama.android2_lesson01.model.Note

/**
 * Class for managing notes
 */

class NotesProviderDataManager(context: Context) :
        BaseProviderDataManager(context),
        NotesDataManager {

    /**
     * Create ArrayList with all data from database table described [NotesTable]
     * and send it by callback. Converts every row to [Note] object
     *
     * @param callback callback for sending ArrayList
     * @see NotesTable
     * @see Note
     */

    override fun loadListOfAllNotes(callback: NotesDataManager.LoadDataCallback) {
        val listOfNotes = ArrayList<Note>()
        var allNotes: Cursor? = null
        try {
            allNotes = query()
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

    override fun createNote(title: String, text: String, callback: NotesDataManager.DataChangedCallback) {
        val values = ContentValues()
        values.put(NotesTable.COLUMN_NAME_TITLE, title)
        values.put(NotesTable.COLUMN_NAME_TEXT, text)
        insert(values)
        callback.onDataChanged()
    }

    /**
     * Remove the row from database table described [NotesTable]
     * corresponding to the given [Note] by it's id on the database
     * and notify changes by callback
     *
     * @param targetNote the [Note] to remove
     * @param callback callback for notify changes
     * @see NotesTable
     * @see Note
     */

    override fun deleteNote(targetNote: Note, callback: NotesDataManager.DataChangedCallback) {
        delete(targetNote.id)
        callback.onDataChanged()
    }

    /**
     * Update the row from database table described [NotesTable]
     * corresponding to the given [Note] by it's id on the database
     * with new column values and notify changes by callback
     *
     * @param targetNote the [Note] to update
     * @param newTitle new title for given note
     * @param newText  new text for given note
     * @param callback callback for notify changes
     * @see NotesTable
     * @see Note
     */

    override fun updateNote(targetNote: Note, newTitle: String, newText: String, callback: NotesDataManager.DataChangedCallback) {
        val values = ContentValues()
        values.put(NotesTable.COLUMN_NAME_TITLE, newTitle)
        values.put(NotesTable.COLUMN_NAME_TEXT, newText)
        update(targetNote.id, values)
        callback.onDataChanged()
    }
}