package com.example.ama.android2_lesson03.ui.markers.mvp

import com.example.ama.android2_lesson03.repo.MarkerListQueryManager
import com.example.ama.android2_lesson03.repo.base.MarkerListManager
import com.example.ama.android2_lesson03.repo.data.model.SimpleMarker
import com.example.ama.android2_lesson03.ui.base.BasePresenter
import com.example.ama.android2_lesson03.ui.markers.base.MarkerPresenter
import com.example.ama.android2_lesson03.ui.markers.base.MarkerView

/**
 * Presenter implementation for [MarkerView]
 *
 * @param <T> view for work with
 */
class MarkerListPresenter<T : MarkerView> : BasePresenter<T>(), MarkerPresenter<T> {

    private val markerManager: MarkerListManager = MarkerListQueryManager()

    override fun getMarkerList() {
        markerManager.getAllMarkers { markers -> view?.showMarkerList(markers) }
    }

    override fun tuneMapCurrentMarker(marker: SimpleMarker) {
        markerManager.saveCurrentMarker(marker)
    }

    override fun editMarkerName(marker: SimpleMarker, newName: String) {
        markerManager.updateMarker(marker, newName) { markers -> view?.showMarkerList(markers) }
    }

    override fun deleteMarker(marker: SimpleMarker) {
        markerManager.deleteMarker(marker) { markers -> view?.showMarkerList(markers) }
    }

    override fun onUpdateMarker(marker: SimpleMarker) {
        markerManager.prepareEditMarkerNameDialog(marker)
        { dialogName, dialogMessage, editingMarker -> view?.showEditDialog(dialogName, dialogMessage, editingMarker) }
    }

}