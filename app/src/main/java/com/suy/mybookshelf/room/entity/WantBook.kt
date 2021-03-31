package com.suy.mybookshelf.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WantBook(
    val cover: String? = null,
    val title: String? = null,
    val author: String? = null,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)
