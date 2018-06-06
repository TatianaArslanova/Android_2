package com.example.ama.android2_lesson03.ui.search;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.base.Presenter;
import com.example.ama.android2_lesson03.base.SearchAddressView;
import com.example.ama.android2_lesson03.ui.Launcher;
import com.example.ama.android2_lesson03.ui.search.mvp.SearchPresenter;

public class SearchFragment extends Fragment implements SearchAddressView {

    private EditText etSearch;
    private TextView tvAddress;
    private Presenter<SearchAddressView> presenter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter = new SearchPresenter<>();
        etSearch = view.findViewById(R.id.et_search);
        tvAddress = view.findViewById(R.id.tv_address);
        view.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSearching(etSearch.getText().toString());
            }
        });
        view.findViewById(R.id.btn_ongmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareToGMap(etSearch.getText().toString());
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        presenter.attachView(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        presenter.detachView();
        super.onStop();
    }

    private void startSearching(String query) {
        presenter.findAddress(query);
    }

    private void prepareToGMap(String query) {
        presenter.sendToGMap(query);
    }

    @Override
    public void showOnGMap(Uri uri) {
        Launcher.sendGoogleMapsIntent((AppCompatActivity) getActivity(), uri);
    }

    @Override
    public void showAddressText(String address) {
        tvAddress.setText(address);
    }
}
