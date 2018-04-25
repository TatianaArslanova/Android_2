package com.example.ama.android2_lesson01.db

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import com.example.ama.android2_lesson01.model.Note

class NotesDataManager(context: Context) : NotesDbHelper(context) {

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

    fun createNote(title: String, text: String) {
        val values = ContentValues()
        values.put(NotesTable.COLOMN_NAME_TITLE, title)
        values.put(NotesTable.COLOMN_NAME_TEXT, text)
        insertRow(NotesTable.TABLE_NAME, values)
    }

    fun removeNote(note: Note) {
        //TODO: remove note
    }

    fun updateNote(note: Note, newTitle: String, newText: String) {
        //TODO: update note
    }
}