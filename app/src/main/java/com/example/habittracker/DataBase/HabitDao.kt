package com.example.habittracker.DataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habittracker.Models.Habit
import java.util.concurrent.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM habit")
    fun getAllHabits(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit ORDER BY dateOfUpdate ASC")
    fun getAllHabitsACSOrderByDate(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit ORDER BY dateOfUpdate DESC")
    fun getAllHabitsDESCOrderByDate(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE name LIKE '%'||(:nameFilter)||'%' ORDER BY dateOfUpdate ASC")
    fun getFilteredAndForwardOrderHabits(nameFilter: String?): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE name LIKE '%'||(:nameFilter)||'%' ORDER BY dateOfUpdate DESC")
    fun getFilteredAndBackwardOrderHabits(nameFilter: String?): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE id = (:selectedId) LIMIT 1")
    fun getHabitById(selectedId: Long): LiveData<Habit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHabit(habit: Habit)

    @Delete
    fun deleteHabit(habit: Habit)
}