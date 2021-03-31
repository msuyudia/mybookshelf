package com.suy.mybookshelf.listener

import com.suy.mybookshelf.room.entity.CurrentlyBook
import com.suy.mybookshelf.utils.ShelfType

interface CurrentlyBookListener {
    fun onCurrentlyBookClicked(data: CurrentlyBook, goToType: ShelfType)
}