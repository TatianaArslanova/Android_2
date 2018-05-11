package com.example.ama.android2_lesson01.ui.rv.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ama.android2_lesson01.R
import com.example.ama.android2_lesson01.model.Note

/**
 * Adapter class for RecyclerView of [com.example.ama.android2_lesson01.ui.rv.ListOfNotesFragment]
 *
 * @see com.example.ama.android2_lesson01.ui.rv.ListOfNotesFragment
 */

class ListOfNotesAdapter(private val listener: ListOfNotesHolder.OnNoteClickListener)
    : RecyclerView.Adapter<ListOfNotesHolder>() {

    private var mData: ArrayList<Note> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOfNotesHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        return ListOfNotesHolder(itemView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ListOfNotesHolder, position: Int) {
        holder.getNoteTitle().text = mData[position].title
        holder.getNoteText().text = mData[position].text
        holder.getBtnDelete().setOnClickListener { listener.onDeleteNoteClick(mData[position]) }
        holder.getNoteView().setOnClickListener { listener.onEditNoteClick(mData[position]) }
    }

    /**
     * Set ArrayList of all displaying [Note] objects and notify adapter about changes
     *
     * @param mData ArrayList of notes
     */

    fun setmData(mData: ArrayList<Note>) {
        this.mData = mData
        notifyDataSetChanged()
    }
}