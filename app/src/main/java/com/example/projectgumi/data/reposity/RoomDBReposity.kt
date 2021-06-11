package com.example.projectgumi.data.reposity

import com.example.projectgumi.data.model.CartModel
import com.example.projectgumi.data.room.CartDAO

class RoomDBReposity(private val cartDAO: CartDAO) {
    suspend fun insertCart(cart: CartModel) = cartDAO.insertCart(cart)
    suspend fun getAllCart(): MutableList<CartModel> = cartDAO.getAllCart()
    suspend fun deleteCart(cartId: Int) = cartDAO.delete(cartId)
    suspend fun deleteAllCart() = cartDAO.deleteAllCart()
    suspend fun updateAmount(cartId: Int, amount: Int) = cartDAO.updateAmount(cartId, amount)
}