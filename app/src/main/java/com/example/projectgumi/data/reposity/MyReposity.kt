package com.example.projectgumi.data.reposity

import com.example.projectgumi.data.api.ApiService
import com.example.projectgumi.data.model.ProductRespone
import com.example.projectgumi.data.model.StatusRespone
import com.example.projectgumi.data.model.UserModel
import retrofit2.Response

class MyReposity(private val apiService: ApiService) {
    suspend fun checkUser(userId: String): Response<StatusRespone> = apiService.checkUser(userId)
    suspend fun updatePhoneUser(
        key: String,
        userId: String,
        userName: String,
        phoneNumber: String
    ) = apiService.updatePhoneUser(key, userId, userName, phoneNumber)
    suspend fun updateAddress(
        key: String,
        id: String,
        city: String,
        district: String,
        wards: String,
        street: String
    ) = apiService.updateAddress(key, id, city, district, wards, street)
}