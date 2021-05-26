package com.example.domain.Interfaces

import com.example.domain.Models.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {

    fun getLocalHabits(filter: String?, revertSort: Boolean): Flow<List<Habit>>

    suspend fun updateHabitsFromServer()

    suspend fun addLocalHabitsToServer()

    suspend fun saveHabit(habit: Habit)
}