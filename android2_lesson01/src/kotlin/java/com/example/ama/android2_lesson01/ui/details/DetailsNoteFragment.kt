package com.example.ama.android2_lesson01.ui.details

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ama.android2_lesson01.NotesApp
import com.example.ama.android2_lesson01.R
import kotlinx.android.synthetic.main.fragment_details_note.*

class DetailsNoteFragment : Fragment(),
        View.OnClickListener {

    companion object {
        const val TARGET_NOTE = "target_note"

        fun newInstance(): DetailsNoteFragment = DetailsNoteFragment()
    }

    private lateinit var mListener: OnSaveNoteClickListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSaveNoteClickListener) {
            mListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_save.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_save) {
            val title = et_note_title.text.toString()
            val text = et_note_text.text.toString()
            NotesApp.dataManager.createNote(title, text)
            mListener.sendResult()
        }
    }

    interface OnSaveNoteClickListener {
        fun sendResult()
    }
}
