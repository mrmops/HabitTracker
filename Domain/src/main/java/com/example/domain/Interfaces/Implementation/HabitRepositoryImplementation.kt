package com.example.domain.Interfaces.Implementation

import com.example.domain.Interfaces.HabitRepository
import com.example.domain.Models.Habit
import com.example.domain.OuterInterfaces.HabitDataBaseService
import com.example.domain.OuterInterfaces.HabitNetworkRepository
import kotlinx.coroutines.flow.Flow
import java.util.*

class HabitRepositoryImplementation(
    private val habitDBService: HabitDataBaseService,
    private val habitNetworkRepository: HabitNetworkRepository
) : HabitRepository {

    override fun getLocalHabits(
        filter: String?,
        revertSort: Boolean
    ): Flow<List<Habit>> = habitDBService.filterAndSortHabitsByDate(filter, revertSort)


    override suspend fun updateHabitsFromServer() =
        habitNetworkRepository.getAllHabits()
            .forEach { habit ->
                habit.uploadOnServer = true
                habit.wasUpdatedLocal = false
                habitDBService.insertHabit(habit)
            }

    override suspend fun addLocalHabitsToServer() {
        habitDBService.getAllHabits()
            .filter { !it.uploadOnServer || it.wasUpdatedLocal }
            .forEach { updateHabitOrId(it) }
    }

    override suspend fun saveHabit(habit: Habit) {
        habit.dateOfUpdate = Date()
        habit.wasUpdatedLocal = true
        updateHabitOrId(habit)
    }

    private suspend fun updateHabitOrId(habit:Habit){
        val id = addOrUpdateOnServer(habit)
        habit.wasUpdatedLocal = false
        if (!habit.uploadOnServer)
            habitDBService.updateId(habit, id)
        else {
            habitDBService.updateHabit(habit)
        }
    }

    private suspend fun addOrUpdateOnServer(habit: Habit): UUID =
        habitNetworkRepository.addOrUpdate(habit)
}