package com.example.domain.Interfaces

import com.example.domain.Models.Habit
import java.util.*

interface HabitNetworkRepository {
    suspend fun getAllHabits(): List<Habit>

    suspend fun addOrUpdate(habit: Habit): UUID

    suspend fun delete(id: UUID)

    suspend fun doneHabit(doneInfo: Habit)

}
