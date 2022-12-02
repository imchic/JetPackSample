package com.example.tobechain.ui

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TobeChain: Application() {

    companion object {
        const val TAG = "TobeChain"
    }
}