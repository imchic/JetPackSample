package com.example.tobechain.map

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.carto.layers.EditableVectorLayer
import com.carto.layers.Layer
import com.carto.ui.MapEventListener
import com.carto.ui.MapView
import com.carto.vectorelements.BalloonPopup
import com.carto.vectorelements.Line
import com.carto.vectorelements.Point
import com.carto.vectorelements.Polygon
import com.example.tobechain.map.listener.VectorElementEditEventListener
import com.example.tobechain.map.listener.VectorElementSelectEventListener
import kotlinx.coroutines.launch

class MapCustomEventListener(
    private val viewModel: MapViewModel,
    private val _mapView: MapView,
) : MapEventListener() {

    private var _popup: BalloonPopup? = null
    private var _pointSymbol: Point? = null
    private var _lineSymbol: Line? = null
    private var _polygonSymbol: Polygon? = null

    private var _targetLayerNm: String = ""
    private var _targetLayerArr: MutableList<Layer>? = null

    init {

        _targetLayerArr = mutableListOf()

        for (i in 0 until BaseMap.getLayerCount()) {

            _targetLayerNm = BaseMap.getLayerName(i, "name")

            _targetLayerNm.run {
                when (this) {
                    BaseMap.MapLayerName.GROUP.value,
                    BaseMap.MapLayerName.EXPLODED_VIEW.value,
                    BaseMap.MapLayerName.ADD_FLOOR.value,
                    BaseMap.MapLayerName.ADD_LINE.value -> {
                        _targetLayerArr?.add(_mapView.layers.get(i))
                    }

                    else -> {
                        return@run
                    }
                }
            }

        }

        _targetLayerArr?.map {
            BaseMap.selectListener = VectorElementSelectEventListener(it as EditableVectorLayer, viewModel)

            it.run {
                if (it.getMetaDataElement("name").string == BaseMap.MapLayerName.GROUP.value) {
                    vectorEditEventListener = VectorElementEditEventListener(BaseMap.containsDataSource)
                }
                vectorElementEventListener = BaseMap.selectListener

            }
        }

    }

    override fun onMapMoved() {
        super.onMapMoved()
        viewModel.viewModelScope.launch {

            viewModel.setLon(_mapView.focusPos.x)
            viewModel.setLat(_mapView.focusPos.y)

        }
    }


}