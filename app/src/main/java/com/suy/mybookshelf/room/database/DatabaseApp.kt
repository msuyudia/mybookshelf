package com.suy.mybookshelf.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.suy.mybookshelf.room.dao.CurrentlyBookDao
import com.suy.mybookshelf.room.dao.ReadBookDao
import com.suy.mybookshelf.room.dao.WantBookDao
import com.suy.mybookshelf.room.entity.CurrentlyBook
import com.suy.mybookshelf.room.entity.ReadBook
import com.suy.mybookshelf.room.entity.WantBook

@Database(entities = [CurrentlyBook::class, WantBook::class, ReadBook::class], version = 1)
abstract class DatabaseApp : RoomDatabase() {
    abstract fun currentlyDao(): CurrentlyBookDao
    abstract fun wantDao(): WantBookDao
    abstract fun readDao(): ReadBookDao
}