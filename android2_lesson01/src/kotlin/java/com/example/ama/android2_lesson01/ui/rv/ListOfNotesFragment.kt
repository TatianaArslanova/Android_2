package com.example.ama.android2_lesson01.ui.rv

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ama.android2_lesson01.R

class ListOfNotesFragment : Fragment() {

    companion object {
        fun newInstance(): ListOfNotesFragment = ListOfNotesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_of_notes, container, false)
    }
}