package com.example.projectgumi.data.model

import com.google.gson.annotations.SerializedName


data class ProductRespone(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: MutableList<Product>
)