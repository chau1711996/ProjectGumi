package com.example.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgumi.data.model.Catelory
import com.example.projectgumi.data.model.Product
import com.example.projectgumi.data.reposity.MyReposity
import kotlinx.coroutines.launch

class ExploreViewModel(private val res: MyReposity) : ViewModel() {

    val dataCategory = MutableLiveData<MutableList<Catelory>?>()
    val dataProduct = MutableLiveData<MutableList<Product>?>()

    fun loadCatelory() {
        viewModelScope.launch {
            dataCategory.postValue(res.getAllCatelory().body()?.data)
        }
    }

    fun loadProductByCateloryId(cateloryId: Int){
        viewModelScope.launch {
            dataProduct.postValue(res.getProductByCateloryId(cateloryId).body()?.data)
        }
    }

}