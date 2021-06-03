package com.example.projectgumi.data.reposity

import com.example.projectgumi.data.model.CartModel
import com.example.projectgumi.data.model.FavoriteModel
import com.example.projectgumi.data.model.Product
import com.example.projectgumi.data.room.CartDAO
import com.example.projectgumi.data.room.FavoriteDAO

class RoomDBReposity(private val cartDAO: CartDAO, private val favoriteDAO: FavoriteDAO) {
    suspend fun insertCart(cart: CartModel) = cartDAO.insertCart(cart)
    suspend fun getAllCart(): MutableList<CartModel> = cartDAO.getAllCart()
    suspend fun deleteCart(cartId: Int) = cartDAO.delete(cartId)
    suspend fun deleteAllCart() = cartDAO.deleteAllCart()


    suspend fun insertFavorite(product: FavoriteModel) = favoriteDAO.insertFavorite(product)
    suspend fun getAllFavorite(): MutableList<FavoriteModel> = favoriteDAO.getAllFavorite()
    suspend fun deleteFavorite(productId: Int) = favoriteDAO.deleteFavorite(productId)
}