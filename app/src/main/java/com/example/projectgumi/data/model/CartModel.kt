package com.example.projectgumi.data.model

import androidx.lifecycle.MutableLiveData
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cart_table")
data class CartModel(
    @PrimaryKey(autoGenerate = true)
    var cartId: Int,
    val productId: Int,
    val productName: String,
    val unit: String,
    val amount: Int,
    val productPrice: String,
    val url: String
): Serializable

