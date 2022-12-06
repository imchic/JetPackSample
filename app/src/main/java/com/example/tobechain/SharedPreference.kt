package com.example.tobechain

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {
    private val prefsFilename = "Prefs"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFilename, 0)

    fun setNoticePref(item: String, data: Boolean){
        prefs.edit().putBoolean(item, data).apply()
    }
    fun getNoticePref(item: String): Boolean{
        return prefs.getBoolean(item, true)
    }
}