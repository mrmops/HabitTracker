package com.example.data.DataBase

import com.example.data.Models.RemoteHabit
import com.example.data.Models.Mapper
import com.example.domain.OuterInterfaces.HabitDataBaseService
import com.example.domain.Models.Habit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class HabitDataBaseServiceImplementation(
    private val habitDao: HabitDao,
    private val mapper: Mapper<RemoteHabit, Habit>
) : HabitDataBaseService {
    override suspend fun updateId(habit: Habit, id: UUID) = habitDao.updateId(mapHabit(habit), id)

    override fun getAllHabitsLive(): Flow<List<Habit>> =
        habitDao.getAllHabitsLive().map { mapper.mapList(it) }

    override fun getAllHabits(): List<Habit> = mapper.mapList(habitDao.getAllHabits())

    override suspend fun getHabitById(selectedId: UUID): Habit =
        mapHabit(habitDao.getHabitById(selectedId))

    override suspend fun insertHabit(habit: Habit) = habitDao.insertHabit(mapHabit(habit))

    override suspend fun updateHabit(habit: Habit) = habitDao.updateHabit(mapHabit(habit))

    override suspend fun deleteHabit(habit: Habit) = habitDao.deleteHabit(mapHabit(habit))

    override fun filterAndSortHabitsByDate(filter: String?, revertSort: Boolean)
            : Flow<List<Habit>> =
        habitDao.filterAndSortHabitsByDate(filter, revertSort).map { mapper.mapList(it) }

    private fun mapHabit(habit: Habit): RemoteHabit = mapper.reverseMap(habit)

    private fun mapHabit(habit: RemoteHabit): Habit = mapper.map(habit)
}