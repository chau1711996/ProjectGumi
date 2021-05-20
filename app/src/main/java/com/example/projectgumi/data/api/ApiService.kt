package com.example.projectgumi.data.api

import com.example.projectgumi.data.model.ProductRespone
import com.example.projectgumi.data.model.StatusRespone
import com.example.projectgumi.data.model.UserModel
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("user/create_user.php")
    @FormUrlEncoded
    suspend fun createUser(@Field("key") key: String,
                           @Field("userId") userId: String,
                           @Field("userName") userName: String): Response<StatusRespone>
}