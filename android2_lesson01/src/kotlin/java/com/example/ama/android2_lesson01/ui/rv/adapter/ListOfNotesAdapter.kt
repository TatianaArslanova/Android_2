package com.example.ama.android2_lesson01.ui.rv.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ama.android2_lesson01.R
import com.example.ama.android2_lesson01.model.Note
import kotlinx.android.synthetic.main.item_rv.view.*

/**
 * Adapter class for RecyclerView of [com.example.ama.android2_lesson01.ui.rv.ListOfNotesFragment]
 *
 * @see com.example.ama.android2_lesson01.ui.rv.ListOfNotesFragment
 */

class ListOfNotesAdapter(
        private val deleteListener: (Note) -> Unit,
        private val editListener: (Note) -> Unit)
    : RecyclerView.Adapter<ListOfNotesAdapter.ListOfNotesHolder>() {

    private var mData: ArrayList<Note> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOfNotesHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        return ListOfNotesHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListOfNotesHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    /**
     * Set ArrayList of all displaying [Note] objects and notify adapter about changes
     *
     * @param mData ArrayList of notes
     */

    fun setmData(mData: ArrayList<Note>) {
        this.mData = mData
        notifyDataSetChanged()
    }

    /**
     * Class of holder for RecyclerView from [com.example.ama.android2_lesson01.ui.rv.ListOfNotesFragment]
     *
     * @see com.example.ama.android2_lesson01.ui.rv.ListOfNotesFragment
     */

    inner class ListOfNotesHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) {
            itemView.tv_card_note_title.text = note.title
            itemView.tv_card_note_text.text = note.text
            itemView.ibtn_delete_note.setOnClickListener { deleteListener.invoke(note) }
            itemView.cv_note.setOnClickListener { editListener.invoke(note) }
        }
    }
}
