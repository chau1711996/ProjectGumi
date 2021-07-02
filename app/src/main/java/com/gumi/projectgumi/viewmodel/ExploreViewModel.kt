package com.gumi.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumi.projectgumi.data.model.Catelory
import com.gumi.projectgumi.data.model.Product
import com.gumi.projectgumi.data.reposity.MyReposity
import kotlinx.coroutines.launch

class ExploreViewModel(private val res: MyReposity) : ViewModel() {

    val dataCategory = MutableLiveData<MutableList<Catelory>?>()
    val dataProduct = MutableLiveData<MutableList<Product>?>()
    val text = MutableLiveData<String?>()

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

    fun getCateloryByName(name: String){
        viewModelScope.launch {
            val result = res.getCateloryByName(name).body()?.data
            dataCategory.postValue(result)
        }
    }

}