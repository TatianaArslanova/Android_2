package com.example.ama.android2_lesson01.ui.details

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.example.ama.android2_lesson01.R
import com.example.ama.android2_lesson01.model.Note
import com.example.ama.android2_lesson01.ui.Launcher
import com.example.ama.android2_lesson01.ui.details.mvp.DetailsNotePresenter
import com.example.ama.android2_lesson01.ui.details.mvp.base.DetailsNoteView
import com.example.ama.android2_lesson01.ui.details.mvp.base.DetailsPresenter
import kotlinx.android.synthetic.main.fragment_details_note.*

/**
 * Class of fragment for note editing
 */

class DetailsNoteFragment : Fragment(), DetailsNoteView {

    companion object {
        const val TARGET_NOTE = "target_note"

        /**
         * Get new instance of [DetailsNoteFragment] with given parameter
         *
         * @param note note for edit and display in fragment.
         *             May be NULL, then a new empty note will be created later.
         * @return new fragment with given note parameter
         */

        fun newInstance(note: Note?): DetailsNoteFragment {
            val fragment = DetailsNoteFragment()
            val args = Bundle()
            args.putParcelable(TARGET_NOTE, note)
            fragment.arguments = args
            return fragment
        }
    }

    private var mTargetNote: Note? = null
    private var mPresenter: DetailsPresenter<DetailsNoteView>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTargetNote = arguments?.getParcelable(TARGET_NOTE)
        if (mTargetNote != null) {
            et_note_title.setText(mTargetNote?.title)
            et_note_text.setText(mTargetNote?.text)
        }
        btn_save.setOnClickListener { onSaveButtonClick() }
        mPresenter = DetailsNotePresenter()
    }

    override fun onStart() {
        super.onStart()
        mPresenter?.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        mPresenter?.detachView()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.details_note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.mi_remove_note) {
            mPresenter?.deleteNote(mTargetNote)
            return true
        }
        return false
    }

    private fun onSaveButtonClick() {
        val title = et_note_title.text.toString()
        val text = et_note_text.text.toString()
        if (mTargetNote != null) {
            mPresenter?.updateNote(mTargetNote!!, title, text)
        } else {
            mPresenter?.createNote(title, text)
        }
        finishEditing()
    }

    override fun finishEditing() {
        Launcher.back(activity as AppCompatActivity)
    }
}
