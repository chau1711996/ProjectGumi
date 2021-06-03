package com.example.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgumi.data.api.ApiService
import com.example.projectgumi.data.model.Product
import com.example.projectgumi.data.reposity.MyReposity
import com.example.projectgumi.ui.shop.ShopSeeAllFragment
import kotlinx.coroutines.launch

class ShopSeeAllViewModel(private val reposity: MyReposity): ViewModel() {

    val data = MutableLiveData<MutableList<Product>?>()

    fun loadDataFromType(type: String) {
        viewModelScope.launch {
            when (type) {
                ShopSeeAllFragment.TYPE_BESTSELLING -> {
                    data.postValue(reposity.getAllBestSelling().body()?.data)
                }
                ShopSeeAllFragment.TYPE_EXCLUSIVE -> {
                    data.postValue(reposity.getAllExclusive().body()?.data)
                }
            }
        }
    }
}