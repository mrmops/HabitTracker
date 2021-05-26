package com.example.domain.Interfaces

import com.example.domain.Models.Habit
import kotlinx.coroutines.flow.Flow
import java.util.*

interface HabitDataBaseService {

    suspend fun  updateId(habit: Habit, id: UUID)

    fun getAllHabitsLive(): Flow<List<Habit>>

    fun getAllHabits(): List<Habit>

    fun getHabitById(selectedId: UUID): Flow<Habit>

    suspend fun insertHabit(habit: Habit)

    suspend fun updateHabit(habit: Habit)

    suspend fun deleteHabit(habit: Habit)

    fun filterAndSortHabitsByDate(filter: String?, revertSort: Boolean): Flow<List<Habit>>

}
