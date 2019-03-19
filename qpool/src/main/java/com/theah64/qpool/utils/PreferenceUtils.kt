package com.theah64.qpool.utils

import android.content.Context
import android.preference.PreferenceManager

class PreferenceUtils(private val context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun isFirstRun(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_FIRST_RUN, true)
    }

    fun setFirstRun(isFirstRun: Boolean) {
        sharedPreferences.edit()
            .putBoolean(KEY_IS_FIRST_RUN, isFirstRun)
            .apply()
    }

    companion object {
        const val KEY_IS_FIRST_RUN = "is_first_run"
    }
}