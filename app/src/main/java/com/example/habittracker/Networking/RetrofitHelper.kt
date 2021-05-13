package com.example.habittracker.Networking

import com.example.habittracker.Models.HabitType
import com.example.habittracker.Models.Priority
import com.example.habittracker.Networking.Interceptors.AuthInterceptor
import com.example.habittracker.Networking.Serializations.DateSerialization
import com.example.habittracker.Networking.Serializations.HabitPrioritySerialization
import com.example.habittracker.Networking.Serializations.HabitTypeSerialization
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
                .addInterceptor(AuthInterceptor())
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