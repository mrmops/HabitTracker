package com.example.habittracker.ui.fragments.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.Infrastructure.HSVColor
import com.example.habittracker.Models.Habit
import com.example.habittracker.Models.HabitType
import com.example.habittracker.Models.Priority
import com.example.habittracker.Networking.Repositories.Implemetations.HabitRepository
import kotlinx.coroutines.*
import org.jetbrains.annotations.NotNull
import java.util.*
import kotlin.coroutines.CoroutineContext

class HabitViewModel(
    @NotNull private val habit: Habit,
    @NotNull private val habitRepository: HabitRepository
) : ViewModel(),
    CoroutineScope {
    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    private val mutableHabitUpdate: MutableLiveData<Habit> = MutableLiveData()
    private val mutableColorUpdate: MutableLiveData<HSVColor> = MutableLiveData()

    val habitUpdate: LiveData<Habit> = mutableHabitUpdate
    val colorUpdate: LiveData<HSVColor> = mutableColorUpdate

    init {
        mutableHabitUpdate.postValue(habit)
    }

    fun saveHabit() = launch(Dispatchers.IO) { habitRepository.saveHabit(habit) }


    fun submit(
        name: String,
        description: String,
        periodic: Int,
        type: HabitType,
        priority: Priority,
        numberRepeating: Int,
        dateOfUpdate: Date
    ) {
        habit.name = name
        val color = colorUpdate.value
        if (color != null)
            habit.color = color
        habit.description = description
        habit.periodic = periodic
        habit.type = type
        habit.priority = priority
        habit.numberRepeating = numberRepeating
        habit.dateOfUpdate = dateOfUpdate
    }


    fun updateColor(color: HSVColor) {
        mutableColorUpdate.postValue(color)
    }

    override fun onCleared() {
        super.onCleared()
        //coroutineContext.cancelChildren()
    }
}