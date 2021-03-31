package com.suy.mybookshelf.listener

import com.suy.mybookshelf.room.entity.WantBook
import com.suy.mybookshelf.utils.ShelfType

interface WantBookListener {
    fun onWantBookClicked(data: WantBook, goToType: ShelfType)
}