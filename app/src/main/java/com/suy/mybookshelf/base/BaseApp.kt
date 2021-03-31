package com.suy.mybookshelf.base

import android.app.Application
import com.suy.mybookshelf.room.database.DatabaseManager

class BaseApp : Application() {
    override fun onCreate() {
        DatabaseManager.initiate(this)
        super.onCreate()
    }
}