package com.suy.mybookshelf.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.suy.mybookshelf.R
import com.suy.mybookshelf.databinding.DialogLoadingBinding

class LoadingDialog(context: Context) : Dialog(context) {
    private lateinit var binding: DialogLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCancelable(false)
        window?.setBackgroundDrawableResource(R.color.transparent)
    }
}