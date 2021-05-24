package com.example.projectgumi.utils

import android.net.Uri
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import coil.load
import com.example.projectgumi.R
import com.google.android.material.textfield.TextInputEditText

object Binding {
    @BindingAdapter("loadImage")
    @JvmStatic
    fun loadImageView(imageView: ImageView, url: Uri?) {
        imageView.load(url){
            placeholder(R.drawable.ic_launcher_background)
            error(R.drawable.ic_launcher_foreground)
        }
    }
    @BindingAdapter("getItemSpinner")
    @JvmStatic
    fun getItemSpinner(spinner: AppCompatSpinner, textItemChoose: MutableLiveData<String?>) {
        textItemChoose.postValue(spinner.selectedItem.toString())
    }
    @BindingAdapter("getTextChange")
    @JvmStatic
    fun getTextChange(textView: TextInputEditText, textItemChoose: MutableLiveData<String?>) {
        textView.doAfterTextChanged {
            textItemChoose.postValue(it.toString())
        }
    }

}