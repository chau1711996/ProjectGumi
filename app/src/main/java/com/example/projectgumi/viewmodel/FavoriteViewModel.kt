package com.example.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgumi.R
import com.example.projectgumi.data.model.CartModel
import com.example.projectgumi.data.model.Catelory
import com.example.projectgumi.data.model.ImageSlideModel
import com.example.projectgumi.data.model.Product
import com.example.projectgumi.data.reposity.MyReposity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.filterList

class FavoriteViewModel(private val reposity: MyReposity) : ViewModel() {

    val dataProduct = MutableLiveData<MutableList<Product>>()

}