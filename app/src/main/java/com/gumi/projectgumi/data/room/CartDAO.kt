package com.gumi.projectgumi.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gumi.projectgumi.data.model.CartModel


@Dao
interface CartDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCart(cart : CartModel)

    @Query("SELECT * FROM cart_table")
    suspend fun getAllCart(): MutableList<CartModel>

    @Query("UPDATE cart_table SET amount = :amount WHERE cartId = :cartId")
    suspend fun updateAmount(cartId: Int, amount: Int)


    @Query("DELETE FROM cart_table WHERE cartId= :cartId")
    suspend fun delete(cartId: Int)

    @Query("DELETE FROM cart_table")
    suspend fun deleteAllCart()
}