package com.gumi.projectgumi.data.model

import java.io.Serializable

data class Product(
    var productId: Int,
    var name: String,
    var unit: String,
    var price: String,
    var cateloryId: Int,
    var url: String
): Serializable