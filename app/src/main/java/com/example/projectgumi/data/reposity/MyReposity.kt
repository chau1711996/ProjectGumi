package com.example.projectgumi.data.reposity

import com.example.projectgumi.data.api.ApiService
import com.example.projectgumi.data.model.ProductRespone
import com.example.projectgumi.data.model.StatusRespone
import com.example.projectgumi.data.model.UserModel
import retrofit2.Response

class MyReposity(private val apiService: ApiService) {
    suspend fun createUser(userModel: UserModel): Response<StatusRespone> = apiService.createUser("insert",userModel.userId, userModel.userName)
}