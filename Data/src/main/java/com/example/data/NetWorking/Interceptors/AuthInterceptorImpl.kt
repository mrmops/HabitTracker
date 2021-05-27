package com.example.data.NetWorking.Interceptors

import com.example.data.NetWorking.ApiConfiguration
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptorImpl : AuthInterceptor {

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
