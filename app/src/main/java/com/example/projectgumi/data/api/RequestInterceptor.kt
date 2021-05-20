package com.example.gumiproject8.data

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = chain.request().url
        val url = originalHttpUrl.newBuilder().build()
        val request = originalRequest.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}