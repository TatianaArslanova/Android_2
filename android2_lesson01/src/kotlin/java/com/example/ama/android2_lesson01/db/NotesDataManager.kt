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
     * Get all data from database table described [NotesTable]
     * converts every row to [Note] object
     *
     * @return ArrayList that contains all existing notes from database
     * @see NotesTable
     * @see Note
     */

    fun getListOfAllNotes(): ArrayList<Note> {
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
        return listOfNotes
    }

    /**
     * Add new row to database table described [NotesTable]
     *
     * @param title title of new note
     * @param text  text of new note
     * @see NotesTable
     */

    fun createNote(title: String, text: String) {
        val values = ContentValues()
        values.put(NotesTable.COLOMN_NAME_TITLE, title)
        values.put(NotesTable.COLOMN_NAME_TEXT, text)
        insertRow(NotesTable.TABLE_NAME, values)
    }

    /**
     * Remove the row from database table described [NotesTable]
     * corresponding to the given [Note]
     *
     * @param note the [Note] to remove
     * @see NotesTable
     * @see Note
     */

    fun removeNote(note: Note) {
        val args = arrayOf(note.id.toString())
        deleteRow(NotesTable.TABLE_NAME, NotesTable.SQL_WHERE_ID, args)
    }

    /**
     * Update the row from database table described [NotesTable]
     * corresponding to the given [Note] with new params
     *
     * @param note     the [Note] to update
     * @param newTitle new title for given note
     * @param newText  new text for given note
     * @see NotesTable
     * @see Note
     */

    fun updateNote(note: Note, newTitle: String, newText: String) {
        val values = ContentValues()
        values.put(NotesTable.COLOMN_NAME_TITLE, newTitle)
        values.put(NotesTable.COLOMN_NAME_TEXT, newText)
        val args = arrayOf(note.id.toString())
        updateRow(NotesTable.TABLE_NAME, values, NotesTable.SQL_WHERE_ID, args)
    }
}