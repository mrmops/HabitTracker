package com.example.data.DataBase

import androidx.room.*
import com.example.data.Models.RemoteHabit
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
abstract class HabitDao {

    @Transaction
    open suspend fun  updateId(remoteHabit: RemoteHabit, id: UUID){
        deleteHabit(remoteHabit)
        remoteHabit.uploadOnServer = true
        remoteHabit.serverId = id
        insertHabit(remoteHabit)
    }

    @Query("SELECT * FROM remotehabit")
    abstract fun getAllHabitsLive(): Flow<List<RemoteHabit>>

    @Query("SELECT * FROM remotehabit")
    abstract fun getAllHabits(): List<RemoteHabit>

    @Query("SELECT * FROM remotehabit ORDER BY dateOfUpdate ASC")
    abstract fun getAllHabitsForwardOrderByDate(): Flow<List<RemoteHabit>>

    @Query("SELECT * FROM remotehabit ORDER BY dateOfUpdate DESC")
    abstract fun getAllHabitsBackwardOrderByDate(): Flow<List<RemoteHabit>>

    @Query("SELECT * FROM remotehabit WHERE name LIKE '%'||(:nameFilter)||'%' ORDER BY dateOfUpdate ASC")
    abstract fun getFilteredAndForwardSortHabitsByDate(nameFilter: String?): Flow<List<RemoteHabit>>

    @Query("SELECT * FROM remotehabit WHERE name LIKE '%'||(:nameFilter)||'%' ORDER BY dateOfUpdate DESC")
    abstract fun getFilteredAndBackwardSortHabitsByDate(nameFilter: String?): Flow<List<RemoteHabit>>

    @Query("SELECT * FROM remotehabit WHERE serverId = (:selectedId) LIMIT 1")
    abstract suspend fun getHabitById(@TypeConverters(com.example.data.DataBase.Converters.UUIDConverter::class)selectedId: UUID): RemoteHabit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertHabit(remoteHabit: RemoteHabit)

    @Update
    abstract suspend fun updateHabit(remoteHabit: RemoteHabit)

    @Delete
    abstract suspend fun deleteHabit(remoteHabit: RemoteHabit)
}