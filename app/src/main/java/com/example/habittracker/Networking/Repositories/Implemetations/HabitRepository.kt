package com.example.habittracker.Networking.Repositories.Implemetations

import androidx.lifecycle.LiveData
import com.example.habittracker.DataBase.HabitDao
import com.example.habittracker.DataBase.filterAndSortHabitsByDate
import com.example.habittracker.Models.Habit
import com.example.habittracker.Networking.Dtos.toDto
import com.example.habittracker.Networking.Repositories.BaseNetworkRepository
import com.example.habittracker.Networking.Services.HabitNetworkService
import java.util.*

class HabitRepository(
    private val habitNetworkService: HabitNetworkService,
    private val habitDao: HabitDao
) : BaseNetworkRepository() {
    companion object {
        private val LOG_TAG = HabitRepository::class.java.name
    }

    override val logTag: String get() = LOG_TAG
    private val delay: Long = 1500L

    fun getLocalHabits(filter: String?, revertSort: Boolean): LiveData<List<Habit>> =
       habitDao.filterAndSortHabitsByDate(filter, revertSort)


    suspend fun updateHabitsFromServer() =
        doWhileNotSuccessWithResult(delay) { habitNetworkService.getAllHabits() }
            .forEach { habitDto ->
                val habit = habitDto.toHabit()
                habit.uploadOnServer = true
                habit.wasUpdatedLocal = false
                habitDao.insertHabit(habit)
            }

    suspend fun addLocalHabitsToServer() {
        habitDao.getAllHabits()
            .filter { !it.uploadOnServer || it.wasUpdatedLocal }
            .forEach { habit ->
                val id = addOrUpdateOnServer(habit)
                habit.wasUpdatedLocal = false
                if (!habit.uploadOnServer)
                    habitDao.updateId(habit, id)
                else {
                    habitDao.updateHabit(habit)
                }
            }
    }

    suspend fun saveHabit(habit: Habit) {
        habit.dateOfUpdate = Date()
        habit.wasUpdatedLocal = true
        habitDao.insertHabit(habit)
        val id = addOrUpdateOnServer(habit)
        if (!habit.uploadOnServer)
            habitDao.updateId(habit, id)
        else
            habitDao.updateHabit(habit)

    }

    private suspend fun addOrUpdateOnServer(habit: Habit): UUID =
        doWhileNotSuccessWithResult(delay) { habitNetworkService.addOrUpdate(habit.toDto()) }.id
}