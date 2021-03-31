package com.suy.mybookshelf.sharedpreference

import android.content.Context

class PrefManager(context: Context) {
    companion object {
        private const val PREF_NAME = "MyBookshelf"
        private const val FIRST_APP = "First App"
    }

    private val pref by lazy { context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE) }

    fun isFirstApp() = pref.getBoolean(FIRST_APP, true)

    fun setFirstApp() = pref.edit().putBoolean(FIRST_APP, false).apply()
}