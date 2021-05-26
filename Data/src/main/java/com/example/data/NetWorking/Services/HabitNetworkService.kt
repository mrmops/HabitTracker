package com.example.data.NetWorking.Services

import com.example.data.NetWorking.Dtos.HabitDoneDto
import com.example.data.NetWorking.Dtos.HabitDto
import com.example.data.NetWorking.Dtos.UuidDto
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