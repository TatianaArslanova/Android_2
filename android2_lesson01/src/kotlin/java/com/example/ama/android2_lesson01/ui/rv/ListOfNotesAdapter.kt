package com.example.ama.android2_lesson01.ui.rv

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ama.android2_lesson01.R
import com.example.ama.android2_lesson01.model.Note

/**
 * Adapter class for RecyclerView of [ListOfNotesFragment]
 *
 * @see ListOfNotesFragment
 */

class ListOfNotesAdapter(private var mData: ArrayList<Note>,
                         private val listener: ListOfNotesHolder.OnNoteClickListener)
    : RecyclerView.Adapter<ListOfNotesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOfNotesHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        return ListOfNotesHolder(itemView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ListOfNotesHolder, position: Int) {
        holder.getNoteTitle().text = mData[position].title
        holder.getNoteText().text = mData[position].text
        holder.getBtnDelete().setOnClickListener { listener.onDeleteNoteClick(mData[position]) }
        holder.getNoteView().setOnClickListener { listener.onNoteClick(mData[position]) }
    }

    /**
     * Set ArrayList of all displaying [Note] objects
     *
     * @param mData ArrayList of notes
     */

    fun setmData(mData: ArrayList<Note>) {
        this.mData = mData
    }
}