package com.example.tobechain.map

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.carto.ui.MapEventListener
import com.carto.ui.MapView
import kotlinx.coroutines.launch

class MapCustomEventListener(
    private val viewModel: MapViewModel,
    private val _mapView: MapView,
) : MapEventListener() {

    override fun onMapMoved() {
        super.onMapMoved()
        viewModel.viewModelScope.launch {

            viewModel.setLon(_mapView.options.baseProjection.fromWgs84(_mapView.focusPos).x)
            viewModel.setLat(_mapView.options.baseProjection.fromWgs84(_mapView.focusPos).y)

            //viewModel.lon.value = _mapView.options.baseProjection.fromWgs84(_mapView.focusPos).x
            //viewModel.lat.value = _mapView.options.baseProjection.fromWgs84(_mapView.focusPos).y
        }
    }


}