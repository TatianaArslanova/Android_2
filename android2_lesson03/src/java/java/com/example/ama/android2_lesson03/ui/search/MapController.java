package com.example.ama.android2_lesson03.ui.search;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.model.UserQueryManager;
import com.example.ama.android2_lesson03.ui.search.base.BaseController;
import com.example.ama.android2_lesson03.ui.search.base.SearchOnTheMapView;
import com.example.ama.android2_lesson03.ui.search.base.SearchPresenter;
import com.example.ama.android2_lesson03.utils.PermissionManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapController extends BaseController {
    private SearchPresenter<SearchOnTheMapView> presenter;
    private OnPermissionRequiredCallback permissionCallback;

    public MapController(SearchPresenter<SearchOnTheMapView> presenter, OnPermissionRequiredCallback permissionCallback) {
        this.presenter = presenter;
        this.permissionCallback = permissionCallback;
    }

    public void tuneMap() {
        if (isMapAttached()) {
            if (PermissionManager.checkPermission(PocketMap.getInstance(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
                setMapUi();
            } else {
                permissionCallback.onPermissionRequired(Manifest.permission.ACCESS_COARSE_LOCATION, PermissionManager.TUNE_MAP_REQUEST);
            }
        }
    }

    private void setMapUi() {
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setAllGesturesEnabled(true);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                presenter.findAddressByLatLng(latLng);
            }
        });
        presenter.findMyLocation();
    }

    public void setMarkerOnTheMap(String address, LatLng latLng, float zoom) {
        if (isMapAttached()) {
            map.clear();
            map.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(address));
            setCameraOnLocation(latLng);
        }
    }

    public void setCameraOnLocation(LatLng latLng) {
        if (isMapAttached()) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, UserQueryManager.DEFAULT_ZOOM));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case PermissionManager.TUNE_MAP_REQUEST: {
                    tuneMap();
                    break;
                }
                case PermissionManager.FIND_MY_LOCATION_REQUEST: {
                    presenter.findMyLocation();
                    break;
                }
            }
        }
    }

    public interface OnPermissionRequiredCallback {
        void onPermissionRequired(String permission, int requestCode);
    }
}
