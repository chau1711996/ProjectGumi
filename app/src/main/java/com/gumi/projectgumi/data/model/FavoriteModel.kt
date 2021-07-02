package com.gumi.projectgumi.data.model

import java.io.Serializable

data class FavoriteModel(
    val id: Int,
    var productId: Int,
    var name: String,
    var unit: String,
    var price: String,
    var url: String,
    var userId: String
): Serializable