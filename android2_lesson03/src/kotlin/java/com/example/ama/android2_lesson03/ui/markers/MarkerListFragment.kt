package com.example.ama.android2_lesson03.ui.markers

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.ama.android2_lesson03.R
import com.example.ama.android2_lesson03.repo.model.SimpleMarker
import com.example.ama.android2_lesson03.ui.markers.base.MarkerPresenter
import com.example.ama.android2_lesson03.ui.markers.base.MarkerView
import com.example.ama.android2_lesson03.ui.markers.mvp.MarkerListPresenter

class MarkerListFragment : ListFragment(), MarkerView {

    private lateinit var adapter: ArrayAdapter<SimpleMarker>
    private var presenter: MarkerPresenter<MarkerView>? = null

    companion object {
        fun newInstance() = MarkerListFragment()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        presenter = MarkerListPresenter()
        adapter = ArrayAdapter(activity, android.R.layout.simple_list_item_1, ArrayList<SimpleMarker>())
        listAdapter = adapter
        registerForContextMenu(listView)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        presenter?.attachView(this)
        presenter?.getMarkerList()
        super.onStart()
    }

    override fun onStop() {
        presenter?.detachView()
        super.onStop()
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity.menuInflater.inflate(R.menu.marker_list_fragment_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val info = item?.menuInfo as AdapterView.AdapterContextMenuInfo
        when (item.itemId) {
            R.id.mi_delete ->
                presenter?.deleteMarker(adapter.getItem(info.position))
        }
        return super.onContextItemSelected(item)
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        presenter?.sendMarker(adapter.getItem(position))
        activity.supportFragmentManager.popBackStack()
    }

    override fun showMarkerList(markers: ArrayList<SimpleMarker>) {
        adapter.clear()
        adapter.addAll(markers)
        adapter.notifyDataSetChanged()
    }


}