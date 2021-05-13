package com.example.projectgumi.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.projectgumi.R
import com.example.projectgumi.databinding.ProgressBarBinding

object Utils {
    const val RC_SIGN_IN = 999
    const val TAG = "TAG"

    const val PHONE_SIGN_IN = 998

    fun showProgressBar(context: Context, message: String): AlertDialog{
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        val binding = ProgressBarBinding.inflate(LayoutInflater.from(context))
        binding.textMessage.text = message
        builder.setView(binding.root)
        return builder.create()
    }

}