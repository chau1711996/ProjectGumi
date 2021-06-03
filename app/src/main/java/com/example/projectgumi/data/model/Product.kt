package com.example.projectgumi.data.model

import android.graphics.drawable.Drawable

data class Product(
    var productId: Int,
    var name: String,
    var unit: String,
    var price: String,
    var cateloryId: Int,
    var url: String
)