package com.example.habittracker.Networking.Interceptors

import android.content.Context
import android.net.ConnectivityManager
import com.example.habittracker.Networking.ApiConfiguration
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
            .addHeader(
                "Authorization",
                ApiConfiguration.AUTH_KEY
            )
        val newRequest = builder.build()
        var result: Response
        runBlocking(Dispatchers.IO){
            while (true) {
                delay(1500)
                if(isOnline()) {
                    val localResult = chain.proceed(newRequest)
                    if(localResult.isSuccessful) {
                        result = localResult
                        break
                    }
                    else{
                        localResult.close()
                    }
                }
            }
        }

        return result
    }

    private fun isOnline():Boolean{
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}
