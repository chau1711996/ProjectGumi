package com.example.projectgumi.data.model

import android.net.Uri
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

data class UserModel(
    val userId: String,
    val userName: String,
    val phoneNumber: String,
    val city: String,
    val district: String,
    val wards: String,
    val street: String
) : Serializable
