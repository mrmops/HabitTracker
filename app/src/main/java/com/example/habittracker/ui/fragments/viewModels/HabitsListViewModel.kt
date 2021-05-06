package com.example.habittracker.ui.fragments.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.habittracker.DataBase.HabitDao
import com.example.habittracker.DataBase.filterAndSortHabitsByDate
import com.example.habittracker.Models.Habit
import com.example.habittracker.Networking.Dtos.toDto
import com.example.habittracker.Networking.Services.HabitNetworkService
import kotlinx.coroutines.*
import org.jetbrains.annotations.NotNull
import kotlin.coroutines.CoroutineContext

class HabitsListViewModel(
    @NotNull private val habitDao: HabitDao,
    @NotNull private val habitService: HabitNetworkService
) :
    ViewModel(), CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    private var filteredParams: FilteredParams = FilteredParams(null, false)

    private val habitFilteredParams: MutableLiveData<FilteredParams> = MutableLiveData()

    val listHabits: LiveData<List<Habit>> =
        Transformations.switchMap(habitFilteredParams) { param ->
            habitDao.filterAndSortHabitsByDate(param.nameFilter, param.invertSort)
        }

    init {
        habitFilteredParams.postValue(filteredParams)
        synchHabits()
    }

    private fun synchHabits() = launch(Dispatchers.IO) {
        uploadFromServer()
        addLocalHabitsToServer()
    }

    private suspend fun uploadFromServer() {
        habitService.getAllHabits()
            .forEach { habitDto ->
                val habit = habitDto.toHabit()
                habit.uploadOnServer = true
                habitDao.insertHabit(habit)
            }
    }

    private suspend fun addLocalHabitsToServer() {
        habitDao.getAllHabits()
            .filter { !it.uploadOnServer }
            .forEach { habit ->
                val createDto = habit.toDto()
/*                var json = JsonObject()
                val gson = GsonBuilder()
                    .setLenient()
                    .registerTypeAdapter(HabitType::class.java, HabitTypeSerialization())
                    .registerTypeAdapter(Priority::class.java, HabitPrioritySerialization())
                    .registerTypeAdapter(Date::class.java, DateSerialization())
                    .create()
                val str = gson.toJson(habit1)
                val str2 = str*/
                val id = habitService.addOrUpdate(createDto)
                habitDao.deleteHabit(habit)
                habit.serverId = id.id
                habitDao.insertHabit(habit)
            }
    }

    fun changeSortDirection(direction: SortDirection) {
        val newRevertedValue = when (direction) {
            SortDirection.Forward -> false
            SortDirection.Backward -> true
        }
        if (newRevertedValue != filteredParams.invertSort) {
            filteredParams.invertSort = newRevertedValue
            habitFilteredParams.postValue(filteredParams)
        }
    }

    fun changeFilter(filter: String) {
        filteredParams.nameFilter = filter
        habitFilteredParams.postValue(filteredParams)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancelChildren()
    }

    enum class SortDirection {
        Forward,
        Backward
    }

    class FilteredParams(var nameFilter: String?, @NotNull var invertSort: Boolean)
}


