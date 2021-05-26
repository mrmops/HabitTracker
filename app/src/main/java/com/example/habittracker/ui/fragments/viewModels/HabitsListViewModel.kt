package com.example.habittracker.ui.fragments.viewModels

import androidx.lifecycle.*
import com.example.domain.Models.Habit
import com.example.domain.Interfaces.HabitRepository
import kotlinx.coroutines.*
import org.jetbrains.annotations.NotNull
import kotlin.coroutines.CoroutineContext

class HabitsListViewModel(
    @NotNull private val habitRepository: HabitRepository
) :
    ViewModel(), CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }



    private var filteredParams: FilteredParams = FilteredParams(null, false)

    private val habitFilteredParams: MutableLiveData<FilteredParams> = MutableLiveData()

    val listHabits: LiveData<List<Habit>> =
        Transformations.switchMap(habitFilteredParams) { param ->
            habitRepository.getLocalHabits(param.nameFilter, param.invertSort).asLiveData()
        }

    init {
        habitFilteredParams.postValue(filteredParams)
        updateHabits()
        viewModelScope
    }

    private fun updateHabits() = launch(Dispatchers.IO) {
        habitRepository.updateHabitsFromServer()
        habitRepository.addLocalHabitsToServer()
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


