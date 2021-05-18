package com.example.projectgumi.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.projectgumi.R

object Binding {
    @BindingAdapter("loadImageDrawable")
    @JvmStatic
    fun loadImageView(imageView: ImageView, drawblabe: Int) {
        imageView.load(drawblabe){
            placeholder(R.drawable.ic_launcher_background)
        }
    }
}