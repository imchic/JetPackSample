package com.example.tobechain.ui

import android.app.AppComponentFactory
import android.app.Application
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TobeChain: Application() {

    override fun onCreate() {
        super.onCreate()


    }

    companion object {
        const val TAG = "TobeChain"
    }
}