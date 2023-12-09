package com.alerdoci.marvelsuperheroes.app.common.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {

    companion object {
        private const val PREFS_NAME = "marvel_settings"

        // Preference keys
        const val APP_THEME_INT = "theme_settings"
    }

    private var prefs: SharedPreferences

    init {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun putInt(key: String, value: Int) {
        val prefsEditor = prefs.edit()
        prefsEditor.putInt(key, value)
        prefsEditor.apply()
    }

    fun getInt(key: String, defValue: Int): Int {
        return prefs.getInt(key, defValue)
    }

}
