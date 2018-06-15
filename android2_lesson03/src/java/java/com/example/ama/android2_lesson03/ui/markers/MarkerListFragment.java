package com.example.ama.android2_lesson03.ui.markers;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.repo.model.SimpleMarker;
import com.example.ama.android2_lesson03.ui.markers.base.MarkerPresenter;
import com.example.ama.android2_lesson03.ui.markers.base.MarkerView;
import com.example.ama.android2_lesson03.ui.markers.mvp.MarkerListPresenter;

import java.util.ArrayList;

public class MarkerListFragment extends ListFragment implements MarkerView {
    private ArrayAdapter<SimpleMarker> adapter;
    private MarkerPresenter<MarkerView> presenter;

    public static MarkerListFragment newInstance() {
        return new MarkerListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        presenter = new MarkerListPresenter<>();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<SimpleMarker>());
        setListAdapter(adapter);
        registerForContextMenu(getListView());
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showMarkerList(ArrayList<SimpleMarker> markers) {
        adapter.clear();
        adapter.addAll(markers);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        presenter.attachView(this);
        presenter.getMarkerList();
        super.onStart();
    }

    @Override
    public void onStop() {
        presenter.detachView();
        super.onStop();
    }

    @Override
    public void onListItemClick(ListView l, View v, final int position, long id) {
        presenter.sendMarker(adapter.getItem(position));
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (getActivity() != null) {
            getActivity().getMenuInflater().inflate(R.menu.marker_list_fragment_context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.mi_delete:
                presenter.deleteMarker(adapter.getItem(info.position));
                break;
        }
        return super.onContextItemSelected(item);
    }
}
