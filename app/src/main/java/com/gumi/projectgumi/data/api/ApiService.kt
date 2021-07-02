package com.gumi.projectgumi.data.api

import com.gumi.projectgumi.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("user/check_user.php")
    @FormUrlEncoded
    suspend fun checkUser(
        @Field("userId") userId: String
    ): Response<StatusRespone>

    @GET("user/getUserById.php")
    suspend fun getUserById(
        @Query("userId") userId: String
    ): Response<UserRespone>

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
        @Field("userName") userName: String,
        @Field("city") city: String,
        @Field("district") district: String,
        @Field("wards") wards: String,
        @Field("street") street: String
    ): Response<StatusRespone>

    @GET("product/get_all_bestSelling.php")
    suspend fun getAllBestSelling(): Response<ProductRespone>

    @GET("product/get_all_bestSelling.php?limit=6")
    suspend fun getBestSellingLimit(): Response<ProductRespone>

    @GET("product/get_all_exclusive.php")
    suspend fun getAllExclusive(): Response<ProductRespone>

    @GET("product/get_all_exclusive.php?limit=6")
    suspend fun getExclusiveLimit(): Response<ProductRespone>

    @GET("product/get_image_by_id.php")
    suspend fun getImageByProductId(@Query("productId") productId: Int): Response<ImagesRespone>

    @GET("product/get_catelory.php")
    suspend fun getAllCatelory(): Response<CateloryRespone>

    @GET("product/getProductByCateloryId.php")
    suspend fun getProductByCateloryId(@Query("cateloryId") cateloryId: Int): Response<ProductRespone>

    @GET("product/getProductById.php")
    suspend fun getProductById(@Query("productId") productId: Int): Response<ProductDetailRespone>

    @GET("product/getProductByName.php")
    suspend fun getProductByName(@Query("name") name: String): Response<ProductRespone>

    @GET("product/insertOrders.php")
    suspend fun insertOrders(
        @Query("id") id: Int,
        @Query("delivery") delivery: String,
        @Query("payment") payment: String,
        @Query("amount") amount: String,
        @Query("money") money: String,
        @Query("userId") userId: String,
    ): Response<StatusRespone>

    @GET("product/getOrdersByIdUser.php")
    suspend fun getOrdersByIdUser(@Query("userId") userId: String): Response<OrdersRespone>

    @GET("product/getCateloryByName.php")
    suspend fun getCateloryByName(@Query("name") name: String): Response<CateloryRespone>

    @GET("product/insertFavorite.php")
    suspend fun insertFavorite(
        @Query("id") id: Int,
        @Query("productId") productId: Int,
        @Query("name") name: String,
        @Query("unit") unit: String,
        @Query("price") price: String,
        @Query("url") url: String,
        @Query("userId") userId: String,
    ): Response<StatusRespone>

    @GET("product/checkFavorite.php")
    suspend fun checkFavorite(
        @Query("productId") productId: Int,
        @Query("userId") userId: String,
    ): Response<StatusRespone>

    @GET("product/getFavoriteByUserId.php")
    suspend fun getFavoriteByUserId(
        @Query("userId") userId: String
    ): Response<FavoriteRespone>
}