package com.gumi.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumi.projectgumi.data.model.*
import com.gumi.projectgumi.data.reposity.MyReposity
import kotlinx.coroutines.launch

class SearchStoreViewModel(private val res: MyReposity) : ViewModel() {

    val text = MutableLiveData<String?>()
    val dataProduct = MutableLiveData<MutableList<Product>?>()

    fun getProductByName(name: String){
        viewModelScope.launch {
            dataProduct.postValue(res.getProductByName(name).body()?.data)
        }
    }

}