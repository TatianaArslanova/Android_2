package com.example.ama.android2_lesson01.model

import android.os.Parcel
import android.os.Parcelable
import com.example.ama.android2_lesson01.NotesApp
import com.example.ama.android2_lesson01.R

class Note(val id: Long = 0,
           val title: String = NotesApp.instance.resources.getString(R.string.default_note_title),
           val text: String = NotesApp.instance.resources.getString(R.string.default_note_text))
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
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }

}