package com.gumi.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumi.projectgumi.data.model.Product
import com.gumi.projectgumi.data.reposity.MyReposity
import com.gumi.projectgumi.ui.shop.ShopSeeAllFragment
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