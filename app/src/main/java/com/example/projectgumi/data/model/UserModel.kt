package com.example.projectgumi.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

data class UserModel(
    val userId: String,
    val userName: String,
    val phoneNumber: String? = null,
    val city: String? = null,
    val district: String? = null,
    val wards: String? = null,
    val street: String? = null,
) : Serializable