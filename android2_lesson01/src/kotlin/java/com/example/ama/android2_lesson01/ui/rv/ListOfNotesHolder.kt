package com.example.ama.android2_lesson01.ui.rv

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.example.ama.android2_lesson01.model.Note
import kotlinx.android.synthetic.main.item_rv.view.*

/**
 * Class of holder for RecyclerView from [ListOfNotesFragment]
 *
 * @see ListOfNotesFragment
 */

class ListOfNotesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun getNoteTitle(): TextView = itemView.tv_card_note_title
    fun getNoteText(): TextView = itemView.tv_card_note_text
    fun getBtnDelete(): ImageButton = itemView.ibtn_delete_note
    fun getNoteView(): CardView = itemView.cv_note

    interface OnNoteClickListener {
        fun onNoteClick(note: Note)
        fun onDeleteNoteClick(note: Note)
    }
}