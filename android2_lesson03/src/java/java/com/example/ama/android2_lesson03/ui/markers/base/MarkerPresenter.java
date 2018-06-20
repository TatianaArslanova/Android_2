package com.example.ama.android2_lesson03.ui.markers.base;

import com.example.ama.android2_lesson03.repo.data.model.SimpleMarker;
import com.example.ama.android2_lesson03.ui.base.Presenter;

public interface MarkerPresenter<T extends MarkerView> extends Presenter<T> {
    void getMarkerList();

    void tuneMapCurrentMarker(SimpleMarker marker);

    void editMarkerName(SimpleMarker marker, String newName);

    void deleteMarker(SimpleMarker marker);

    void onUpdateMarker(SimpleMarker marker);

}
