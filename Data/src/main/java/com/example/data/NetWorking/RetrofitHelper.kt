package com.example.data.NetWorking

import com.example.domain.Models.HabitType
import com.example.domain.Models.Priority
import com.example.data.NetWorking.Interceptors.AuthInterceptorImpl
import com.example.data.NetWorking.Serializations.DateSerialization
import com.example.data.NetWorking.Serializations.HabitPrioritySerialization
import com.example.data.NetWorking.Serializations.HabitTypeSerialization
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class RetrofitHelper {

    companion object {

        fun newInstance(): Retrofit {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptorImpl())
                .addInterceptor(HttpLoggingInterceptor())
                .build()

            val gson = GsonBuilder()
                .setLenient()
                .registerTypeAdapter(HabitType::class.java, HabitTypeSerialization())
                .registerTypeAdapter(Priority::class.java, HabitPrioritySerialization())
                .registerTypeAdapter(Date::class.java, DateSerialization())
                .create()

            return Retrofit.Builder()
                .baseUrl(ApiConfiguration.HOST_NAME)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}