package com.example.projectgumi.di

import com.example.gumiproject8.data.RequestInterceptor
import com.example.projectgumi.data.api.ApiService
import com.example.projectgumi.data.reposity.MyReposity
import com.example.projectgumi.utils.Utils
import com.example.projectgumi.viewmodel.LoginViewModel
import com.example.projectgumi.viewmodel.ShopViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val applicationModule = module {
    single { provideHttpLoggingInterceptor() }
    single { provideOkHttp() }
    single { provideRetrofit(get()) }
    single { MyReposity(get()) }

    viewModel { LoginViewModel(get()) }
    viewModel { ShopViewModel(get()) }
}

private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

private fun provideOkHttp(): OkHttpClient {
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(RequestInterceptor())
    return okHttpClient.build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient): ApiService {

    val gson = GsonBuilder().setLenient().create()

    val retrofit = Retrofit.Builder()
        .baseUrl(Utils.API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()



    return retrofit.create(ApiService::class.java)
}