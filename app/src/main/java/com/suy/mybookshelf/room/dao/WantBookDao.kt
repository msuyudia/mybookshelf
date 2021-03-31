package com.suy.mybookshelf.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.suy.mybookshelf.room.entity.WantBook

@Dao
interface WantBookDao {
    @Insert
    suspend fun saveBook(wantBook: WantBook): Long

    @Insert
    suspend fun saveBooks(wantBooks: List<WantBook>): LongArray

    @Query("SELECT * FROM wantbook")
    suspend fun getBook(): List<WantBook>?

    @Delete
    suspend fun deleteBook(wantBook: WantBook): Int
}