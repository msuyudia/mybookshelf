package com.suy.mybookshelf.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.suy.mybookshelf.room.entity.CurrentlyBook

@Dao
interface CurrentlyBookDao {
    @Insert
    suspend fun saveBook(currentlyBook: CurrentlyBook): Long

    @Insert
    suspend fun saveBooks(currentlyBooks: List<CurrentlyBook>): LongArray

    @Query("SELECT * FROM currentlybook")
    suspend fun getBook(): List<CurrentlyBook>?

    @Delete
    suspend fun deleteBook(currentlyBook: CurrentlyBook): Int
}