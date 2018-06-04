package com.example.ama.android2_lesson01.ui.rv

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.*
import com.example.ama.android2_lesson01.R
import com.example.ama.android2_lesson01.model.Note
import com.example.ama.android2_lesson01.ui.Launcher
import com.example.ama.android2_lesson01.ui.MainActivity
import com.example.ama.android2_lesson01.ui.base.Presenter
import com.example.ama.android2_lesson01.ui.rv.adapter.ListOfNotesAdapter
import com.example.ama.android2_lesson01.ui.rv.mvp.ListOfNotesPresenter
import com.example.ama.android2_lesson01.ui.rv.mvp.base.ListOfNotesView
import com.example.ama.android2_lesson01.ui.rv.mvp.base.ListPresenter
import kotlinx.android.synthetic.main.fragment_list_of_notes.*

/**
 * Class of fragment that contains RecyclerView for displaying notes
 */

class ListOfNotesFragment : Fragment(), ListOfNotesView {

    companion object {
        const val RV_SPAN_COUNT = 2

        fun newInstance() = ListOfNotesFragment()
    }

    private lateinit var mAdapter: ListOfNotesAdapter
    private var mPresenter: ListPresenter<ListOfNotesView>? = null


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
        mAdapter = ListOfNotesAdapter(
                { it -> mPresenter?.deleteNote(it) },
                { it -> Launcher.runDetailsNoteFragment(activity as MainActivity, true, it) })
        rv_list_of_notes.adapter = mAdapter
        mPresenter = ListOfNotesPresenter()
    }

    override fun onStart() {
        super.onStart()
        mPresenter?.attachView(this)
        mPresenter?.loadData()
    }

    override fun onStop() {
        super.onStop()
        mPresenter?.detachView()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.list_of_notes_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.mi_add_note) {
            Launcher.runDetailsNoteFragment(activity as MainActivity, true, null)
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
}