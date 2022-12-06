package com.example.tobechain.map

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    private val _lon = mutableStateOf(0.0)
    val lon: State<Double> = _lon

    private val _lat = mutableStateOf(0.0)
    val lat: State<Double> = _lat

    private val _zoom = mutableStateOf(0.0)
    val zoom: State<Double> = _zoom

    private val _tilt = mutableStateOf(0.0)
    val tilt: State<Double> = _tilt

    private val _select = mutableStateOf(0)
    val select: State<Int> = _select

    private val _editModeStatus = mutableStateOf(false)
    val editModeStatus: State<Boolean> = _editModeStatus

    fun setLon(lon: Double) {
        _lon.value = lon
    }

    fun setLat(lat: Double) {
        _lat.value = lat
    }

    fun setZoom(zoom: Double) {
        _zoom.value = zoom
    }

    fun setTilt(tilt: Double) {
        _tilt.value = tilt
    }

    fun setSelectCnt(size: Int) {
        _select.value = size
    }

    fun setEditModeStatus(status: Boolean) = viewModelScope.launch {
        _editModeStatus.value = status
    }
}