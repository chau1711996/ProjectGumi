package com.example.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgumi.data.model.*
import com.example.projectgumi.data.reposity.MyReposity
import com.example.projectgumi.data.reposity.RoomDBReposity
import kotlinx.coroutines.launch

class CheckoutViewModel(private val res: MyReposity, private val roomDBReposity: RoomDBReposity) : ViewModel() {

    val status = MutableLiveData<StatusRespone?>()

    fun insertOrders(orders: OrdersModel){
        viewModelScope.launch {
            status.postValue(res.insertOrders(orders).body())
        }
    }

    fun deleteAllCart(){
        viewModelScope.launch {
            roomDBReposity.deleteAllCart()
        }
    }

}