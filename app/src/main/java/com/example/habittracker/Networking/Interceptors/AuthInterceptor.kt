package com.example.habittracker.Networking.Interceptors

import com.example.habittracker.Networking.ApiConfiguration
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
            .addHeader(
                "Authorization",
                ApiConfiguration.AUTH_KEY
            )
        val newRequest = builder.build()

        return chain.proceed(newRequest)
    }
}
