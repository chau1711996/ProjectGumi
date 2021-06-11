package com.example.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgumi.data.model.CartModel
import com.example.projectgumi.data.model.FavoriteModel
import com.example.projectgumi.data.model.Product
import com.example.projectgumi.data.reposity.RoomDBReposity
import kotlinx.coroutines.launch

class CartViewModel(private val roomDBReposity: RoomDBReposity) : ViewModel() {
    val dataCart = MutableLiveData<MutableList<CartModel>?>()
    var sumMoney = MutableLiveData<String>()
    var statusAddAllCart = MutableLiveData<Boolean?>()

    fun loadDataCart() {
        viewModelScope.launch {
            val result = roomDBReposity.getAllCart()
            dataCart.postValue(result)
        }
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
            loadDataCart()
        }
    }

    fun updateAmount(cartId: Int, amount: Int) {
        viewModelScope.launch {
            roomDBReposity.updateAmount(cartId, amount)
            loadDataCart()
        }
    }

    fun insertCart(product: Product) {
        viewModelScope.launch {
            roomDBReposity.insertCart(
                CartModel(
                    0,
                    product.productId,
                    product.name,
                    product.unit,
                    1,
                    product.price,
                    product.url
                )
            )
        }
    }

    fun insertCartByFavorite(list: MutableList<FavoriteModel>){
        statusAddAllCart.postValue(false)
        viewModelScope.launch {
            list.forEach {
                roomDBReposity.insertCart(
                    CartModel(
                        0,
                        it.productId,
                        it.name,
                        it.unit,
                        1,
                        it.price,
                        it.url
                    )
                )
            }
            statusAddAllCart.postValue(true)
        }
    }
}