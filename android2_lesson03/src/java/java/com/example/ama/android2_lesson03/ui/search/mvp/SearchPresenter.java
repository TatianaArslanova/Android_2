package com.example.ama.android2_lesson03.ui.search.mvp;

import android.net.Uri;

import com.example.ama.android2_lesson03.base.BasePresenter;
import com.example.ama.android2_lesson03.base.SearchAddressView;
import com.example.ama.android2_lesson03.model.base.QueryManager;

public class SearchPresenter<T extends SearchAddressView> extends BasePresenter<T> {

    @Override
    public void findAddress(String query) {
        queryManager.getFullLocationName(query, new QueryManager.OnFullNamePreparedCallback() {
            @Override
            public void onSuccess(String fullLocationName) {
                view.showAddressText(fullLocationName);
            }
        });
    }

    @Override
    public void sendToGMap(String query) {
        queryManager.prepareGMapUri(query, new QueryManager.OnUriPreparedCallback() {
            @Override
            public void onSuccess(Uri uri) {
                view.showOnGMap(uri);
            }
        });
    }
}
