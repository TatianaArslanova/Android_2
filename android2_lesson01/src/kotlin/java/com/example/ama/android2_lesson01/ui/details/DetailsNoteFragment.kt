package com.example.ama.android2_lesson01.ui.details

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ama.android2_lesson01.NotesApp
import com.example.ama.android2_lesson01.R
import com.example.ama.android2_lesson01.model.Note
import kotlinx.android.synthetic.main.fragment_details_note.*

class DetailsNoteFragment : Fragment(),
        View.OnClickListener {

    companion object {
        const val TARGET_NOTE = "target_note"

        fun newInstance(note: Note?): DetailsNoteFragment {
            val fragment = DetailsNoteFragment()
            val args = Bundle()
            args.putParcelable(TARGET_NOTE, note)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mListener: OnSaveNoteClickListener
    private lateinit var mTargetNote: Note

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
        val argNote: Note? = arguments?.getParcelable(TARGET_NOTE)
        if (argNote != null) {
            mTargetNote = argNote
            et_note_title.setText(mTargetNote.title)
            et_note_text.setText(mTargetNote.text)
        }
        btn_save.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_save) {
            val title = et_note_title.text.toString()
            val text = et_note_text.text.toString()
            if (::mTargetNote.isInitialized) {
                NotesApp.dataManager.updateNote(mTargetNote, title, text)
            } else {
                NotesApp.dataManager.createNote(title, text)
            }
            mListener.sendResult()
        }
    }

    interface OnSaveNoteClickListener {
        fun sendResult()
    }
}
