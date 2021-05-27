package com.example.data.NetWorking.Repositories.Implemetations

import android.util.Log
import com.example.data.Models.RemoteHabit
import com.example.data.Models.Mapper
import com.example.data.NetWorking.Dtos.HabitDoneDto
import com.example.data.NetWorking.Dtos.UuidDto
import com.example.data.NetWorking.Dtos.toDto
import com.example.data.NetWorking.Repositories.BaseNetworkRepository
import com.example.data.NetWorking.Services.HabitNetworkService
import com.example.domain.OuterInterfaces.HabitNetworkRepository
import com.example.domain.Models.Habit
import java.util.*

class NetworkRepositoryImplementation(
    private val networkService: HabitNetworkService,
    private val mapper: Mapper<RemoteHabit, Habit>
) : BaseNetworkRepository(), HabitNetworkRepository {


    override val logTag: String = NetworkRepositoryImplementation::class.java.name

    private val delay = 1500L

    override suspend fun getAllHabits(): List<Habit> =
        doWhileNotSuccessWithResult(delay) {
            val mapList = mapper.mapList(
                networkService.getAllHabits().map { it.toHabit() })
            Log.d(logTag, "Get all Habits")
            mapList
        }

    override suspend fun addOrUpdate(habit: Habit): UUID =
        doWhileNotSuccessWithResult(delay) {
            val id = networkService.addOrUpdate(mapper.reverseMap(habit).toDto()).id
            Log.d(logTag, "Habit was add or update with id $id")
            id
        }

    override suspend fun delete(id: UUID) {
        doWhileNotSuccessWithResult(delay) {
            networkService.delete(UuidDto(id))
            Log.d(logTag, "Habit was delete with id $id")
        }
    }

    override suspend fun doneHabit(doneInfo: Habit) {
        doWhileNotSuccessWithResult(delay) {
            networkService.doneHabit(
                HabitDoneDto(
                    doneInfo.dateOfUpdate ?: Date(), doneInfo.serverId
                )
            )
        }
    }
}