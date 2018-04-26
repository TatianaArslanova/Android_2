package com.example.ama.android2_lesson01.model

import android.os.Parcel
import android.os.Parcelable
import com.example.ama.android2_lesson01.NotesApp
import com.example.ama.android2_lesson01.R

/**
 * Class describing notes
 */

class Note(val id: Long,
           val title: String,
           val text: String)
    : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {

        /**
         * Get new instance on NoteBuilder for Note creation
         *
         * @return new NoteBuilder instance
         */

        fun builder(): NoteBuilder = NoteBuilder()

        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }

    /**
     * Class for Note building
     */

    class NoteBuilder {
        var id: Long = 0
            private set
        var title = NotesApp.instance.resources.getString(R.string.default_note_title)!!
            private set
        var text = NotesApp.instance.resources.getString(R.string.default_note_text)!!
            private set

        /**
         * Set id for the future note
         *
         * @param id id for the note
         * @return current NoteBuilder
         */

        fun id(id: Long): NoteBuilder {
            this.id = id
            return this
        }

        /**
         * Set title for the future note
         *
         * @param title title for the note
         * @return current NoteBuilder
         */

        fun title(title: String): NoteBuilder {
            if (title != "") {
                this.title = title
            }
            return this
        }

        /**
         * Set text for the future note
         *
         * @param text id for the note
         * @return current NoteBuilder
         */

        fun text(text: String): NoteBuilder {
            if (text != "") {
                this.text = text
            }
            return this
        }

        /**
         * Build new note
         *
         * @return new note
         */

        fun build(): Note = Note(id, title, text)

    }
}