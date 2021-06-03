package com.example.projectgumi.data.reposity

import com.example.projectgumi.data.api.ApiService
import com.example.projectgumi.data.model.*
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

    suspend fun getAllBestSelling(): Response<ProductRespone> = apiService.getAllBestSelling()
    suspend fun getBestSellingLimit(): Response<ProductRespone> = apiService.getBestSellingLimit()
    suspend fun getAllExclusive(): Response<ProductRespone> = apiService.getAllExclusive()
    suspend fun getExclusiveLimit(): Response<ProductRespone> = apiService.getExclusiveLimit()
    suspend fun getImageByProductId(productId: Int): Response<ImagesRespone> =
        apiService.getImageByProductId(productId)

    suspend fun getAllCatelory(): Response<CateloryRespone> = apiService.getAllCatelory()
    suspend fun getProductByCateloryId(cateloryId: Int): Response<ProductRespone> =
        apiService.getProductByCateloryId(cateloryId)

    suspend fun getProductById(productId: Int): Response<ProductDetailRespone> =
        apiService.getProductById(productId)

    suspend fun getProductByName(name: String): Response<ProductRespone> =
        apiService.getProductByName(name)

    suspend fun insertOrders(orders: OrdersModel): Response<StatusRespone> =
        apiService.insertOrders(orders.id, orders.delivery, orders.payment, orders.amount, orders.money)

    suspend fun getOrders(): Response<OrdersRespone> = apiService.getOrders()
}