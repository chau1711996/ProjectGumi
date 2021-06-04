package com.example.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgumi.data.model.CartModel
import com.example.projectgumi.data.reposity.RoomDBReposity
import kotlinx.coroutines.launch

class CartViewModel(private val roomDBReposity: RoomDBReposity) : ViewModel() {
    val dataCart = MutableLiveData<MutableList<CartModel>?>()
    var sumMoney = MutableLiveData<String>()
    val statusLoad = MutableLiveData(true)

    fun loadDataCart() {
        viewModelScope.launch {
            val result = roomDBReposity.getAllCart()
            dataCart.postValue(result)
        }
    }

    fun loadSrollCart() {
        statusLoad.postValue(false)
        loadDataCart()
        statusLoad.postValue(true)
    }

    fun sumMoneyCart(list: MutableList<CartModel>) {
        var sum = 0.0
        list.forEach {
            val money = (it.productPrice.substring(1).toDouble() * it.amount)
            sum += money
        }
        sumMoney.postValue("$${sum}")
    }

    fun deleteCart(cartId: Int) {
        viewModelScope.launch {
            roomDBReposity.deleteCart(cartId)
        }
    }
}