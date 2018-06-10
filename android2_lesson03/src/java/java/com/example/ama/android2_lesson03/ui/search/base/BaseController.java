package com.example.ama.android2_lesson03.ui.search.base;

import com.google.android.gms.maps.GoogleMap;

public abstract class BaseController implements Controller {
    protected GoogleMap map;

    @Override
    public void attachMap(GoogleMap map) {
        this.map = map;
        tuneMap();
    }

    @Override
    public void detachMap() {
        map = null;
    }

    @Override
    public boolean isMapAttached() {
        return map != null;
    }
}
