package com.example.habittracker.ui.fragments.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.Models.Habit
import java.util.*
import kotlin.collections.HashMap

class SortedAndFilteredHabitsListViewModel : ViewModel() {

    private val habits: HashMap<UUID, Habit> = HashMap()
    private var filterString: String? = null
    private var revertedSort = false

    private val mutableListHabits: MutableLiveData<List<Habit>> = MutableLiveData()

    val listHabits: LiveData<List<Habit>> = mutableListHabits

    init {
        mutableListHabits.value = habits.values.toList()
    }

    fun addOrUpdateHabit(habit: Habit) {
        habits[habit.id] = habit
        updateList()
    }

    private fun updateList() {
        val habits =
            (if (filterString.isNullOrEmpty())
                this.habits.values
            else
                this.habits.values.filter { it.name.contains(filterString!!, true) })

        if (revertedSort)
            mutableListHabits.postValue(habits.sortedBy{ it.dateOfUpdate })
        else
            mutableListHabits.postValue(habits.sortedByDescending { it.dateOfUpdate })
    }

    fun changeSortDirection(direction: SortDirection) {
        val newRevertedValue = when (direction) {
            SortDirection.Forward -> false
            SortDirection.Backward -> true
        }
        if (newRevertedValue != revertedSort) {
            revertedSort = newRevertedValue
            updateList()
        }
    }

    fun changeFilter(filter: String) {
        filterString = filter
        updateList()
    }

    enum class SortDirection {
        Forward,
        Backward
    }
}


