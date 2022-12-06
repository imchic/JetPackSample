package com.example.tobechain.map.listener

import com.carto.layers.EditableVectorLayer
import com.carto.layers.VectorElementEventListener
import com.carto.ui.VectorElementClickInfo
import com.carto.vectorelements.Text
import com.example.tobechain.map.BaseMap
import com.example.tobechain.map.MapViewModel

class VectorElementSelectEventListener(private val layer: EditableVectorLayer?, private val viewModel: MapViewModel) :
    VectorElementEventListener() {
    override fun onVectorElementClicked(clickInfo: VectorElementClickInfo): Boolean {

        when (clickInfo.layer.getMetaDataElement("name").string) {
            BaseMap.MapLayerName.EXPLODED_VIEW.value, BaseMap.MapLayerName.GROUP.value, BaseMap.MapLayerName.ADD_FLOOR.value, BaseMap.MapLayerName.ADD_LINE.value -> {
                select(clickInfo)
            }
        }

//        LogUtil.i("선택된 레이어 : $selectLayerName , ${clickInfo.clickPos}")
        return true
    }

    private fun select(element: VectorElementClickInfo) {
        layer?.apply {
            selectedVectorElement = element.vectorElement

            val centerPos = element.vectorElement.geometry.centerPos
//            LogUtil.i(centerPos.toString())

            when (element.vectorElement) {
                is Text -> {
//                    LogUtil.i("vectorElement Type => Text")
                }
                else -> {
//                    LogUtil.i("vectorElement Type => EditableLayer")
                    BaseMap.select(element.vectorElement.geometry, viewModel)
                }
            }

        }
    }
}