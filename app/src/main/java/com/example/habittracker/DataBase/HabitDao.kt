package com.example.habittracker.DataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habittracker.DataBase.Converters.UUIDConverter
import com.example.habittracker.Models.Habit
import java.util.*
import java.util.concurrent.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM habit")
    fun getAllHabitsLive(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit")
    fun getAllHabits(): List<Habit>

    @Query("SELECT * FROM habit ORDER BY dateOfUpdate ASC")
    fun getAllHabitsForwardOrderByDate(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit ORDER BY dateOfUpdate DESC")
    fun getAllHabitsBackwardOrderByDate(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE name LIKE '%'||(:nameFilter)||'%' ORDER BY dateOfUpdate ASC")
    fun getFilteredAndForwardSortHabitsByDate(nameFilter: String?): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE name LIKE '%'||(:nameFilter)||'%' ORDER BY dateOfUpdate DESC")
    fun getFilteredAndBackwardSortHabitsByDate(nameFilter: String?): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE serverId = (:selectedId) LIMIT 1")
    fun getHabitById(@TypeConverters(UUIDConverter::class)selectedId: UUID): LiveData<Habit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)
}