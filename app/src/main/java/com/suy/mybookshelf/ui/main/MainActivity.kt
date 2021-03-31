package com.suy.mybookshelf.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.suy.mybookshelf.adapter.CurrentlyBookAdapter
import com.suy.mybookshelf.adapter.ReadBookAdapter
import com.suy.mybookshelf.adapter.WantBookAdapter
import com.suy.mybookshelf.databinding.ActivityMainBinding
import com.suy.mybookshelf.dialog.LoadingDialog
import com.suy.mybookshelf.listener.CurrentlyBookListener
import com.suy.mybookshelf.listener.ReadBookListener
import com.suy.mybookshelf.listener.WantBookListener
import com.suy.mybookshelf.room.entity.CurrentlyBook
import com.suy.mybookshelf.room.entity.ReadBook
import com.suy.mybookshelf.room.entity.WantBook
import com.suy.mybookshelf.sharedpreference.PrefManager
import com.suy.mybookshelf.utils.ShelfType
import com.suy.mybookshelf.utils.Status
import splitties.toast.longToast

class MainActivity : AppCompatActivity(), CurrentlyBookListener, WantBookListener,
    ReadBookListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private val loading by lazy { LoadingDialog(this) }
    private val showLoading by lazy { if (!loading.isShowing) loading.show() }
    private val hideLoading by lazy { if (loading.isShowing) loading.dismiss() }
    private val pref by lazy { PrefManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initiateViewModel()
    }

    private fun initiateViewModel() {
        when (pref.isFirstApp()) {
            true -> {
                viewModel.insertCurrentlyBook()
                viewModel.insertWantBook()
                viewModel.insertReadBook()
                pref.setFirstApp()
            }
            false -> {
                viewModel.getCurrentlyBooks(false)
                viewModel.getWantBooks(false)
                viewModel.getReadBooks(false)
            }
        }
        viewModel.currentlyLiveData().observe(this, {
            when (it.status) {
                Status.LOADING -> showLoading
                Status.SUCCESS -> {
                    hideLoading
                    binding.rvCurrentlyReading.adapter =
                        CurrentlyBookAdapter(it.data ?: mutableListOf(), this@MainActivity)
                }
                Status.ERROR -> {
                    hideLoading
                    longToast(it.message ?: 0)
                }
            }
        })
        viewModel.wantLiveData().observe(this, {
            when (it.status) {
                Status.LOADING -> showLoading
                Status.SUCCESS -> {
                    hideLoading
                    binding.rvWantToRead.adapter =
                        WantBookAdapter(it.data ?: mutableListOf(), this@MainActivity)
                }
                Status.ERROR -> {
                    hideLoading
                    longToast(it.message ?: 0)
                }
            }
        })
        viewModel.readLiveData().observe(this, {
            when (it.status) {
                Status.LOADING -> showLoading
                Status.SUCCESS -> {
                    hideLoading
                    binding.rvRead.adapter =
                        ReadBookAdapter(it.data ?: mutableListOf(), this@MainActivity)
                }
                Status.ERROR -> {
                    hideLoading
                    longToast(it.message ?: 0)
                }
            }
        })
    }

    override fun onCurrentlyBookClicked(data: CurrentlyBook, goToType: ShelfType) {
        viewModel.changeCurrentlyBookType(data, goToType)
    }

    override fun onWantBookClicked(data: WantBook, goToType: ShelfType) {
        viewModel.changeWantBookType(data, goToType)
    }

    override fun onReadBookClicked(data: ReadBook, goToType: ShelfType) {
        viewModel.changeReadBookType(data, goToType)
    }
}