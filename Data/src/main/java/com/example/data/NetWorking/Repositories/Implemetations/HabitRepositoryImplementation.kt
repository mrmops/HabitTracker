//package com.example.data.NetWorking.Repositories.Implemetations
//
//import com.example.data.DataBase.filterAndSortHabitsByDate
//import com.example.data.Models.Habit
//import com.example.data.NetWorking.Dtos.toDto
//import com.example.data.NetWorking.Repositories.BaseNetworkRepository
//import com.example.data.NetWorking.Services.HabitNetworkService
//import kotlinx.coroutines.flow.Flow
//import java.util.*
//
//class HabitRepositoryImplementation(
//    private val habitNetworkService: HabitNetworkService,
//    private val habitDao: com.example.data.DataBase.HabitDao,
//    private val habitMapper: HabitMapper
//) : BaseNetworkRepository() {
//    companion object {
//        private val LOG_TAG = HabitRepositoryImplementation::class.java.name
//    }
//
//    override val logTag: String get() = LOG_TAG
//    private val delay: Long = 1500L
//
//    fun getLocalHabits(
//        filter: String?,
//        revertSort: Boolean
//    ): Flow<List<Habit>> = habitDao.filterAndSortHabitsByDate(filter, revertSort)
//
//
//    suspend fun updateHabitsFromServer() =
//        doWhileNotSuccessWithResult(delay) { habitNetworkService.getAllHabits() }
//            .forEach { habitDto ->
//                val habit = habitDto.toHabit()
//                habit.uploadOnServer = true
//                habit.wasUpdatedLocal = false
//                habitDao.insertHabit(habit)
//            }
//
//    suspend fun addLocalHabitsToServer() {
//        habitDao.getAllHabits()
//            .filter { !it.uploadOnServer || it.wasUpdatedLocal }
//            .forEach { habit ->
//                val id = addOrUpdateOnServer(habit)
//                habit.wasUpdatedLocal = false
//                if (!habit.uploadOnServer)
//                    habitDao.updateId(habit, id)
//                else {
//                    habitDao.updateHabit(habit)
//                }
//            }
//    }
//
//    suspend fun saveHabit(habit: Habit) {
//        habit.dateOfUpdate = Date()
//        habit.wasUpdatedLocal = true
//        habitDao.insertHabit(habit)
//        val id = addOrUpdateOnServer(habit)
//        if (!habit.uploadOnServer)
//            habitDao.updateId(habit, id)
//        else
//            habitDao.updateHabit(habit)
//
//    }
//
//    private suspend fun addOrUpdateOnServer(habit: Habit): UUID =
//        doWhileNotSuccessWithResult(delay) { habitNetworkService.addOrUpdate(habit.toDto()) }.id
//}