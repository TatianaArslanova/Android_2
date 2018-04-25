package com.example.ama.android2_lesson01.ui.rv

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_rv.view.*

class ListOfNotesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun getNoteTitle(): TextView = itemView.tv_card_note_title
    fun getNoteText(): TextView = itemView.tv_card_note_text

}