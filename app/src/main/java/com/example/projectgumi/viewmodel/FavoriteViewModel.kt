package com.example.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgumi.data.model.*
import com.example.projectgumi.data.reposity.RoomDBReposity
import kotlinx.coroutines.launch

class FavoriteViewModel(private val roomDB: RoomDBReposity) : ViewModel() {

    val dataProduct = MutableLiveData<MutableList<FavoriteModel>?>()

    fun loadData(){
        viewModelScope.launch {
            dataProduct.postValue(roomDB.getAllFavorite())
        }
    }

}