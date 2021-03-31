package com.suy.mybookshelf.listener

import com.suy.mybookshelf.room.entity.ReadBook
import com.suy.mybookshelf.utils.ShelfType

interface ReadBookListener {
    fun onReadBookClicked(data: ReadBook, goToType: ShelfType)
}