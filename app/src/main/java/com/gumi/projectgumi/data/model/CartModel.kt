package com.gumi.projectgumi.data.model

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
    var amount: Int,
    var productPrice: String,
    val url: String
): Serializable

