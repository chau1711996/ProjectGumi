package com.example.projectgumi.data.model

import java.io.Serializable

data class UserModel(
    val userId: String,
    val userName: String,
    val phoneNumber: String? = null,
    val token: String,
    val city: String? = null,
    val district: String? = null,
    val wards: String? = null,
    val street: String? = null,
) : Serializable