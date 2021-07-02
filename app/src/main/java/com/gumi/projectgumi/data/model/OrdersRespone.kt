package com.gumi.projectgumi.data.model

import com.google.gson.annotations.SerializedName


data class OrdersRespone(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: MutableList<OrdersModel>
)