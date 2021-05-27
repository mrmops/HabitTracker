package com.example.domain.Interfaces

import com.example.domain.Models.Habit
import kotlinx.coroutines.flow.Flow
import java.util.*

interface HabitRepository {

    fun getLocalHabits(filter: String?, revertSort: Boolean): Flow<List<Habit>>

    suspend fun updateHabitsFromServer()

    suspend fun addLocalHabitsToServer()

    suspend fun saveHabit(habit: Habit)

    suspend fun doneHabit(habitId: UUID)

    suspend fun remainingReps(habitId: UUID): Int
}