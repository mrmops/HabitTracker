package com.example.habittracker.ui.fragments.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.habittracker.DataBase.HabitDao
import com.example.habittracker.DataBase.HabitsDataBase
import com.example.habittracker.DataBase.filterAndSortHabitsByDate
import com.example.habittracker.Models.Habit
import org.jetbrains.annotations.NotNull

class SortedAndFilteredHabitsListViewModel(@NotNull private val dataBase: HabitsDataBase) :
    ViewModel() {

    private var filteredParams: FilteredParams = FilteredParams(null, false)
    private val habitDao: HabitDao = dataBase.habitDao()

    private val habitFilteredParams: MutableLiveData<FilteredParams> = MutableLiveData()

    val listHabits: LiveData<List<Habit>> =
        Transformations.switchMap(habitFilteredParams) { param ->
            habitDao.filterAndSortHabitsByDate(param.nameFilter, param.invertSort)
        }

    init {
        habitFilteredParams.postValue(filteredParams)
    }

    fun addOrUpdateHabit(habit: Habit) {
        habitDao.insertHabit(habit)
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

    enum class SortDirection {
        Forward,
        Backward
    }
}

class FilteredParams(var nameFilter: String?, @NotNull var invertSort: Boolean)


