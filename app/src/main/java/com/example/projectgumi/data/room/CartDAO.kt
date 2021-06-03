package com.example.projectgumi.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projectgumi.data.model.CartModel


@Dao
interface CartDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCart(cart : CartModel)

    @Query("SELECT * FROM cart_table")
    suspend fun getAllCart(): MutableList<CartModel>
}