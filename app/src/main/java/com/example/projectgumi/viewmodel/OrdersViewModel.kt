package com.example.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgumi.data.model.*
import com.example.projectgumi.data.reposity.MyReposity
import com.example.projectgumi.data.reposity.RoomDBReposity
import kotlinx.coroutines.launch

class OrdersViewModel(private val res: MyReposity) : ViewModel() {

    val dataOrders = MutableLiveData<MutableList<OrdersModel>?>()
    fun getOrders(){
        viewModelScope.launch {
            dataOrders.postValue(res.getOrders().body()?.data)
        }
    }

}