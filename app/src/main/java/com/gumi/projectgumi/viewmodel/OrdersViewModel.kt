package com.gumi.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumi.projectgumi.data.model.*
import com.gumi.projectgumi.data.reposity.MyReposity
import kotlinx.coroutines.launch

class OrdersViewModel(private val res: MyReposity) : ViewModel() {

    val dataOrders = MutableLiveData<MutableList<OrdersModel>?>()
    fun getOrdersByIdUser(userId: String){
        viewModelScope.launch {
            dataOrders.postValue(res.getOrdersByIdUser(userId).body()?.data)
        }
    }

}