package com.example.habittracker.Networking.Services

import com.example.habittracker.Networking.Dtos.HabitDoneDto
import com.example.habittracker.Networking.Dtos.HabitDto
import com.example.habittracker.Networking.Dtos.UuidDto
import retrofit2.http.*

interface HabitNetworkService {
    @GET("habit")
    suspend fun getAllHabits(): List<HabitDto>

    @PUT("habit")
    suspend fun addOrUpdate(@Body habit: HabitDto): UuidDto

    @DELETE("habit")
    suspend fun delete(@Body id: UuidDto)

    @POST("habit_done")
    suspend fun doneHabit(@Body doneInfo: HabitDoneDto)
}