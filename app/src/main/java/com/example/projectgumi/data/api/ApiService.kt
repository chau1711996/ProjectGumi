package com.example.projectgumi.data.api

import com.example.projectgumi.data.model.ProductRespone
import com.example.projectgumi.data.model.StatusRespone
import com.example.projectgumi.data.model.UserModel
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("user/check_user.php")
    @FormUrlEncoded
    suspend fun checkUser(
        @Field("userId") userId: String
    ): Response<StatusRespone>

    @POST("user/user.php")
    @FormUrlEncoded
    suspend fun updatePhoneUser(
        @Field("key") key: String,
        @Field("userId") userId: String,
        @Field("userName") userName: String,
        @Field("phoneNumber") phoneNumber: String
    ): Response<StatusRespone>
    @POST("user/user.php")
    @FormUrlEncoded
    suspend fun updateAddress(
        @Field("key") key: String,
        @Field("userId") userId: String,
        @Field("city") city: String,
        @Field("district") district: String,
        @Field("wards") wards: String,
        @Field("street") street: String
    ): Response<StatusRespone>



}