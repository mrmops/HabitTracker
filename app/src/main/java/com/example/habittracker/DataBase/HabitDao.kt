package com.example.habittracker.DataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habittracker.DataBase.Converters.UUIDConverter
import com.example.habittracker.Models.Habit
import java.util.*
import java.util.concurrent.Flow

@Dao
abstract class HabitDao {

    @Transaction
    open suspend fun  updateId(habit: Habit, id: UUID){
        deleteHabit(habit)
        habit.uploadOnServer = true
        habit.serverId = id
        insertHabit(habit)
    }

    @Query("SELECT * FROM habit")
    abstract fun getAllHabitsLive(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit")
    abstract fun getAllHabits(): List<Habit>

    @Query("SELECT * FROM habit ORDER BY dateOfUpdate ASC")
    abstract fun getAllHabitsForwardOrderByDate(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit ORDER BY dateOfUpdate DESC")
    abstract fun getAllHabitsBackwardOrderByDate(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE name LIKE '%'||(:nameFilter)||'%' ORDER BY dateOfUpdate ASC")
    abstract fun getFilteredAndForwardSortHabitsByDate(nameFilter: String?): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE name LIKE '%'||(:nameFilter)||'%' ORDER BY dateOfUpdate DESC")
    abstract fun getFilteredAndBackwardSortHabitsByDate(nameFilter: String?): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE serverId = (:selectedId) LIMIT 1")
    abstract fun getHabitById(@TypeConverters(UUIDConverter::class)selectedId: UUID): LiveData<Habit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertHabit(habit: Habit)

    @Update
    abstract suspend fun updateHabit(habit: Habit)

    @Delete
    abstract suspend fun deleteHabit(habit: Habit)
}