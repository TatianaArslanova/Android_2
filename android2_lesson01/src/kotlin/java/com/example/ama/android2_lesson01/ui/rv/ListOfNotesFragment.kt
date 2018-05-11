package com.example.ama.android2_lesson01.ui.rv

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.*
import com.example.ama.android2_lesson01.R
import com.example.ama.android2_lesson01.model.Note
import com.example.ama.android2_lesson01.ui.base.Presenter
import com.example.ama.android2_lesson01.ui.rv.adapter.ListOfNotesAdapter
import com.example.ama.android2_lesson01.ui.rv.adapter.ListOfNotesHolder
import com.example.ama.android2_lesson01.ui.rv.mvp.ListOfNotesPresenter
import com.example.ama.android2_lesson01.ui.rv.mvp.ListOfNotesView
import kotlinx.android.synthetic.main.fragment_list_of_notes.*

/**
 * Class of fragment that contains RecyclerView for displaying notes
 */

class ListOfNotesFragment : Fragment(),
        ListOfNotesHolder.OnNoteClickListener,
        ListOfNotesView {

    companion object {
        const val RV_SPAN_COUNT = 2

        fun newInstance(): ListOfNotesFragment = ListOfNotesFragment()
    }

    private lateinit var mAdapter: ListOfNotesAdapter
    private lateinit var mListener: OnDetailsClickListener
    private var mPresenter: Presenter<ListOfNotesView>? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnDetailsClickListener)
            mListener = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_of_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_list_of_notes.layoutManager = GridLayoutManager(context, RV_SPAN_COUNT)
        mAdapter = ListOfNotesAdapter(this)
        rv_list_of_notes.adapter = mAdapter
        mPresenter = ListOfNotesPresenter()
        mPresenter?.attachView(this)
        mPresenter?.loadData()
    }

    override fun onDestroyView() {
        mPresenter?.detachView()
        mPresenter = null
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.list_of_notes_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.mi_add_note) {
            mListener.openEditNote(null)
            return true
        }
        return false
    }

    override fun showNoteList(mData: ArrayList<Note>) {
        mAdapter.setmData(mData)
    }

    override fun showEmptyMessage() {
        tv_no_notes_message.visibility = View.VISIBLE
        rv_list_of_notes.visibility = View.INVISIBLE
    }

    override fun hideEmptyMessage() {
        tv_no_notes_message.visibility = View.INVISIBLE
        rv_list_of_notes.visibility = View.VISIBLE
    }

    override fun onEditNoteClick(note: Note) {
        mListener.openEditNote(note)
    }

    override fun onDeleteNoteClick(note: Note) {
        mPresenter?.deleteNote(note)
    }

    interface OnDetailsClickListener {
        fun openEditNote(note: Note?)
    }
}