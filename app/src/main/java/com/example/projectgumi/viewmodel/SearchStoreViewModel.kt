package com.example.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgumi.data.model.*
import com.example.projectgumi.data.reposity.MyReposity
import com.example.projectgumi.data.reposity.RoomDBReposity
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