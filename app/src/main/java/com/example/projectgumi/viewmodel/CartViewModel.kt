package com.example.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgumi.R
import com.example.projectgumi.data.model.CartModel
import com.example.projectgumi.data.model.Product
import com.example.projectgumi.data.reposity.CartReposity
import com.example.projectgumi.data.reposity.MyReposity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(private val cartReposity: CartReposity) : ViewModel() {
    val dataCart = MutableLiveData<MutableList<CartModel>?>()

    fun loadDataCart(){
        viewModelScope.launch {
            val result = cartReposity.getAllCart()
            dataCart.postValue(result)
        }
    }
}