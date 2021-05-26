package com.example.data.Dagger

import android.content.Context
import androidx.room.Room
import com.example.data.DataBase.HabitDao
import com.example.data.DataBase.HabitDataBaseServiceImplementation
import com.example.data.DataBase.HabitsDataBase
import com.example.data.Models.RemoteHabit
import com.example.data.Models.RemoteHabitMapper
import com.example.data.Models.Mapper
import com.example.data.NetWorking.ApiConfiguration
import com.example.data.NetWorking.Interceptors.AuthInterceptor
import com.example.data.NetWorking.Interceptors.AuthInterceptorImpl
import com.example.data.NetWorking.Repositories.Implemetations.NetworkRepositoryImplementation
import com.example.data.NetWorking.Serializations.DateSerialization
import com.example.data.NetWorking.Serializations.HabitPrioritySerialization
import com.example.data.NetWorking.Serializations.HabitTypeSerialization
import com.example.data.NetWorking.Services.HabitNetworkService
import com.example.domain.Interfaces.HabitDataBaseService
import com.example.domain.Interfaces.HabitNetworkRepository
import com.example.domain.Models.Habit
import com.example.domain.Models.HabitType
import com.example.domain.Models.Priority
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
class DataModule(private val context: Context) {

    @Provides
    fun provideHabitDataBaseService(habitDao: HabitDao, mapper: Mapper<RemoteHabit, Habit>): HabitDataBaseService =
        HabitDataBaseServiceImplementation(habitDao, mapper)

    @Provides
    fun provideHabitNetworkRepository(
        networkService: HabitNetworkService,
        mapper: Mapper<RemoteHabit, Habit>
    ): HabitNetworkRepository = NetworkRepositoryImplementation(networkService, mapper)

    @Provides
    fun provideHabitDao(habitsDataBase: HabitsDataBase): HabitDao = habitsDataBase.habitDao()

    @Singleton
    @Provides
    fun provideHabitsDataBase(): HabitsDataBase {
        return Room.databaseBuilder(
            context,
            HabitsDataBase::class.java,
            HabitsDataBase.DB_NAME
        ).build()
    }

    @Provides
    fun provideHabitNetworkService(retrofit: Retrofit): HabitNetworkService =
        retrofit.create(HabitNetworkService::class.java)

    @Provides
    fun provideRetrofit(authInterceptor: AuthInterceptor): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
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

    @Provides
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptorImpl()

    @Provides
    fun provideHabitMapper(): Mapper<RemoteHabit, Habit> = RemoteHabitMapper()
}