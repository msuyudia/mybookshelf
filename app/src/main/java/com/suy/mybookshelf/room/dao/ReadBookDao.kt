package com.suy.mybookshelf.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.suy.mybookshelf.room.entity.ReadBook

@Dao
interface ReadBookDao {
    @Insert
    suspend fun saveBook(readBook: ReadBook): Long

    @Insert
    suspend fun saveBooks(readBooks: List<ReadBook>): LongArray

    @Query("SELECT * FROM readbook")
    suspend fun getBook(): List<ReadBook>?

    @Delete
    suspend fun deleteBook(readBook: ReadBook): Int
}