package com.example.projectgumi.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

data class StatusRespone(
    @SerializedName("status") val status: String
)