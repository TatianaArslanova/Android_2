package com.example.ama.android2_lesson01.ui.rv

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

class ListOfNotesFragment : Fragment() {

    companion object {
        fun newInstance(): ListOfNotesFragment = ListOfNotesFragment()
    }

    private lateinit var mData: ArrayList<Note>
    private lateinit var mAdapter: ListOfNotesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_of_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mData = NotesApp.dataManager.getListOfAllNotes()
        rv_list_of_notes.layoutManager = GridLayoutManager(context, 2)
        mAdapter = ListOfNotesAdapter(mData)
        rv_list_of_notes.adapter = mAdapter
    }

    fun refresh() {
        mData = NotesApp.dataManager.getListOfAllNotes()
        mAdapter.setmData(mData)
        mAdapter.notifyDataSetChanged()
    }
}