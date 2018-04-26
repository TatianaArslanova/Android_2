package com.example.ama.android2_lesson01.ui.rv

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ama.android2_lesson01.NotesApp
import com.example.ama.android2_lesson01.R
import com.example.ama.android2_lesson01.model.Note
import kotlinx.android.synthetic.main.fragment_list_of_notes.*

/**
 * Class of fragment that contains RecyclerView for displaying notes
 */

class ListOfNotesFragment : Fragment() {

    companion object {
        fun newInstance(): ListOfNotesFragment = ListOfNotesFragment()
    }

    private lateinit var mData: ArrayList<Note>
    private lateinit var mAdapter: ListOfNotesAdapter
    private lateinit var mListener: ListOfNotesHolder.OnNoteClickListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ListOfNotesHolder.OnNoteClickListener)
            mListener = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_of_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mData = NotesApp.dataManager.getListOfAllNotes()
        rv_list_of_notes.layoutManager = GridLayoutManager(context, 2)
        mAdapter = ListOfNotesAdapter(mData, mListener)
        rv_list_of_notes.adapter = mAdapter
    }

    /**
     * Get new data from the database and update the list
     */

    fun refresh() {
        mData = NotesApp.dataManager.getListOfAllNotes()
        mAdapter.setmData(mData)
        mAdapter.notifyDataSetChanged()
    }

    /**
     * Delete given note from the list
     *
     * @param note the note to delete
     */

    fun deleteNote(note: Note) {
        NotesApp.dataManager.removeNote(note)
        refresh()
    }
}