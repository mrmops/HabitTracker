package com.example.habittracker.ui.fragments.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.Infrastructure.HSVColor
import com.example.domain.Interfaces.HabitRepository
import com.example.domain.Models.Habit
import com.example.domain.Models.HabitType
import com.example.domain.Models.Priority
import kotlinx.coroutines.*
import org.jetbrains.annotations.NotNull
import kotlin.coroutines.CoroutineContext

class HabitViewModel(
    @NotNull private val habit: Habit,
    @NotNull private val habitRepository: HabitRepository
) : ViewModel(),
    CoroutineScope {
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e ->
            e.printStackTrace()
            throw e
        }

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
        numberRepeating: Int
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
    }


    fun updateColor(color: HSVColor) {
        mutableColorUpdate.postValue(color)
    }

    override fun onCleared() {
        super.onCleared()
//        coroutineContext.cancelChildren()
    }
}