package com.gumi.projectgumi.data.reposity

import com.gumi.projectgumi.data.model.CartModel
import com.gumi.projectgumi.data.room.CartDAO

class RoomDBReposity(private val cartDAO: CartDAO) {
    suspend fun insertCart(cart: CartModel) = cartDAO.insertCart(cart)
    suspend fun getAllCart(): MutableList<CartModel> = cartDAO.getAllCart()
    suspend fun deleteCart(cartId: Int) = cartDAO.delete(cartId)
    suspend fun deleteAllCart() = cartDAO.deleteAllCart()
    suspend fun updateAmount(cartId: Int, amount: Int) = cartDAO.updateAmount(cartId, amount)
}