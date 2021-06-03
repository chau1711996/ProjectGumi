package com.example.projectgumi.data.reposity

import com.example.projectgumi.data.model.CartModel
import com.example.projectgumi.data.room.CartDAO

class CartReposity(private val cartDAO: CartDAO) {
    suspend fun insertCart(cart: CartModel) = cartDAO.insertCart(cart)
    suspend fun getAllCart(): MutableList<CartModel> = cartDAO.getAllCart()
}