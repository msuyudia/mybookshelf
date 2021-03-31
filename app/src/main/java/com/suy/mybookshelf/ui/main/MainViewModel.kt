package com.suy.mybookshelf.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suy.mybookshelf.R
import com.suy.mybookshelf.room.database.DatabaseManager
import com.suy.mybookshelf.room.entity.CurrentlyBook
import com.suy.mybookshelf.room.entity.ReadBook
import com.suy.mybookshelf.room.entity.WantBook
import com.suy.mybookshelf.utils.Resource
import com.suy.mybookshelf.utils.ShelfType
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val currentlyMutable by lazy { MutableLiveData<Resource<List<CurrentlyBook>>>() }
    private val wantMutable by lazy { MutableLiveData<Resource<List<WantBook>>>() }
    private val readMutable by lazy { MutableLiveData<Resource<List<ReadBook>>>() }

    fun insertCurrentlyBook() {
        viewModelScope.launch {
            val currentlyBook1 = CurrentlyBook(
                "https://images-eu.ssl-images-amazon.com/images/I/51fA9IM9d5L.jpg",
                "Android System Programming",
                "Roger Ye"
            )
            val currentlyBook2 = CurrentlyBook(
                "https://images-na.ssl-images-amazon.com/images/I/41ZOn5+5P2L._SX376_BO1,204,203,200_.jpg",
                "Beginning Android Tablet Programming",
                "Robbie Matthews"
            )
            val list = mutableListOf(currentlyBook1, currentlyBook2)
            currentlyMutable.postValue(Resource.loading())
            val currentlyBooksSaved = DatabaseManager.db.currentlyDao().saveBooks(list)
            when (currentlyBooksSaved.isNotEmpty()) {
                true -> getCurrentlyBooks(true)
                false -> currentlyMutable.postValue(Resource.error(R.string.text_failed_save_currently_read_book))
            }
        }
    }

    fun insertWantBook() {
        viewModelScope.launch {
            val wantBook1 = WantBook(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR7-br8fsHL2fk-GbS8pxELdMF88Uv7qNZrmw&usqp=CAU",
                "Android Programming Tutorials",
                "Mark L. Murphy"
            )
            val wantBook2 = WantBook(
                "https://qph.fs.quoracdn.net/main-qimg-6fd0af40bf8b12c97f487771135e3afc",
                "Learn Android Studio 3",
                "Ted Hagos"
            )
            val list = mutableListOf(wantBook1, wantBook2)
            wantMutable.postValue(Resource.loading())
            val wantBooksSaved = DatabaseManager.db.wantDao().saveBooks(list)
            when (wantBooksSaved.isNotEmpty()) {
                true -> getWantBooks(true)
                false -> wantMutable.postValue(Resource.error(R.string.text_failed_save_want_to_read_book))
            }
        }
    }

    fun insertReadBook() {
        viewModelScope.launch {
            val readBook1 = ReadBook(
                "https://cdn-media-1.freecodecamp.org/images/Ms0wz2zF4WNrHArf2Uf5Z4DoBJZTvoL6A87c",
                "Android App Development FOR DUMMIES",
                "Michael Burton"
            )
            val readBook2 = ReadBook(
                "https://all-ebook.info/uploads/posts/2019-05/medium/1559226955_1484244664.jpg",
                "Learn Kotlin for Android Development",
                "Peter Spath"
            )
            val list = mutableListOf(readBook1, readBook2)
            readMutable.postValue(Resource.loading())
            val readBooksSaved = DatabaseManager.db.readDao().saveBooks(list)
            when (readBooksSaved.isNotEmpty()) {
                true -> getReadBooks(true)
                false -> readMutable.postValue(Resource.error(R.string.text_failed_save_read_book))
            }
        }
    }

    fun changeCurrentlyBookType(currentlyBook: CurrentlyBook, type: ShelfType) {
        viewModelScope.launch {
            currentlyMutable.postValue(Resource.loading())
            val currentlyBookDeleted = DatabaseManager.db.currentlyDao().deleteBook(currentlyBook)
            when (currentlyBookDeleted > 0) {
                true -> {
                    getCurrentlyBooks(true)
                    when (type) {
                        ShelfType.WANT -> {
                            val wantBook = WantBook(
                                currentlyBook.cover,
                                currentlyBook.title,
                                currentlyBook.author
                            )
                            saveWantBook(wantBook)
                        }
                        ShelfType.READ -> {
                            val readBook = ReadBook(
                                currentlyBook.cover,
                                currentlyBook.title,
                                currentlyBook.author
                            )
                            saveReadBook(readBook)
                        }
                        else -> currentlyMutable.postValue(Resource.error(R.string.text_failed_move_currently_read_book))
                    }
                }
                false -> currentlyMutable.postValue(Resource.error(R.string.text_failed_move_currently_read_book))
            }
        }
    }

    private fun saveCurrentlyBook(currentlyBook: CurrentlyBook) {
        viewModelScope.launch {
            currentlyMutable.postValue(Resource.loading())
            val currentlyBookSaved = DatabaseManager.db.currentlyDao().saveBook(currentlyBook)
            when (currentlyBookSaved > 0) {
                true -> getCurrentlyBooks(true)
                false -> currentlyMutable.postValue(Resource.error(R.string.text_failed_save_currently_read_book))
            }
        }
    }

    fun getCurrentlyBooks(isChanged: Boolean) {
        viewModelScope.launch {
            if (!isChanged) currentlyMutable.postValue(Resource.loading())
            val currentlyBooks = DatabaseManager.db.currentlyDao().getBook()
            when (currentlyBooks.isNullOrEmpty()) {
                false -> currentlyMutable.postValue(Resource.success(currentlyBooks))
                true -> currentlyMutable.postValue(Resource.success(mutableListOf()))
            }
        }
    }

    fun changeWantBookType(wantBook: WantBook, type: ShelfType) {
        viewModelScope.launch {
            wantMutable.postValue(Resource.loading())
            val wantBookDeleted = DatabaseManager.db.wantDao().deleteBook(wantBook)
            when (wantBookDeleted > 0) {
                true -> {
                    getWantBooks(true)
                    when (type) {
                        ShelfType.CURRENTLY -> {
                            val currentlyBook =
                                CurrentlyBook(wantBook.cover, wantBook.title, wantBook.author)
                            saveCurrentlyBook(currentlyBook)
                        }
                        ShelfType.READ -> {
                            val readBook = ReadBook(wantBook.cover, wantBook.title, wantBook.author)
                            saveReadBook(readBook)
                        }
                        else -> wantMutable.postValue(Resource.error(R.string.text_failed_move_want_to_read_book))
                    }
                }
                false -> wantMutable.postValue(Resource.error(R.string.text_failed_move_want_to_read_book))
            }
        }
    }

    private fun saveWantBook(wantBook: WantBook) {
        viewModelScope.launch {
            val wantBookSaved = DatabaseManager.db.wantDao().saveBook(wantBook)
            when (wantBookSaved > 0) {
                true -> getWantBooks(true)
                false -> wantMutable.postValue(Resource.error(R.string.text_failed_save_want_to_read_book))
            }
        }
    }

    fun getWantBooks(isChanged: Boolean) {
        viewModelScope.launch {
            if (!isChanged) wantMutable.postValue(Resource.loading())
            val wantBooks = DatabaseManager.db.wantDao().getBook()
            when (wantBooks.isNullOrEmpty()) {
                false -> wantMutable.postValue(Resource.success(wantBooks))
                true -> wantMutable.postValue(Resource.success(mutableListOf()))
            }
        }
    }

    fun changeReadBookType(readBook: ReadBook, type: ShelfType) {
        viewModelScope.launch {
            readMutable.postValue(Resource.loading())
            val readBookDeleted = DatabaseManager.db.readDao().deleteBook(readBook)
            when (readBookDeleted > 0) {
                true -> {
                    getReadBooks(true)
                    when (type) {
                        ShelfType.CURRENTLY -> {
                            val currentlyBook =
                                CurrentlyBook(readBook.cover, readBook.title, readBook.author)
                            saveCurrentlyBook(currentlyBook)
                        }
                        ShelfType.WANT -> {
                            val wantBook = WantBook(readBook.cover, readBook.title, readBook.author)
                            saveWantBook(wantBook)
                        }
                        else -> wantMutable.postValue(Resource.error(R.string.text_failed_move_read_book))
                    }
                }
                false -> wantMutable.postValue(Resource.error(R.string.text_failed_move_read_book))
            }
        }
    }

    private fun saveReadBook(readBook: ReadBook) {
        viewModelScope.launch {
            val readBookSaved = DatabaseManager.db.readDao().saveBook(readBook)
            when (readBookSaved > 0) {
                true -> getReadBooks(true)
                false -> wantMutable.postValue(Resource.error(R.string.text_failed_save_read_book))
            }
        }
    }

    fun getReadBooks(isChanged: Boolean) {
        viewModelScope.launch {
            if (!isChanged) readMutable.postValue(Resource.loading())
            val readBooks = DatabaseManager.db.readDao().getBook()
            when (readBooks.isNullOrEmpty()) {
                false -> readMutable.postValue(Resource.success(readBooks))
                true -> readMutable.postValue(Resource.success(mutableListOf()))
            }
        }
    }

    fun currentlyLiveData(): LiveData<Resource<List<CurrentlyBook>>> = currentlyMutable
    fun wantLiveData(): LiveData<Resource<List<WantBook>>> = wantMutable
    fun readLiveData(): LiveData<Resource<List<ReadBook>>> = readMutable
}
