package com.example.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgumi.data.model.*
import com.example.projectgumi.data.reposity.MyReposity
import kotlinx.coroutines.launch

class FavoriteViewModel(private val res: MyReposity) : ViewModel() {

    val dataProduct = MutableLiveData<MutableList<FavoriteModel>?>()

    fun loadData(userId: String){
        viewModelScope.launch {
            dataProduct.postValue(res.getFavoriteByUserId(userId).body()?.data)
        }
    }

}