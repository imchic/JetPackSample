package com.example.tobechain.map

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {

    private val _lon = mutableStateOf(0.0)
    val lon: State<Double> = _lon

    private val _lat = mutableStateOf(0.0)
    val lat: State<Double> = _lat

    fun setLon(lon: Double) {
        _lon.value = lon
    }

    fun setLat(lat: Double) {
        _lat.value = lat
    }
}