package com.example.habittracker.ui.fragments.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.Infrastructure.HSVColor
import com.example.habittracker.Models.Habit
import com.example.habittracker.Models.HabitType
import com.example.habittracker.Models.Priority
import java.util.*

class HabitViewModel(private val habit: Habit) : ViewModel() {

    private val mutableHabitUpdate: MutableLiveData<Habit> = MutableLiveData()
    private val mutableColorUpdate: MutableLiveData<HSVColor> = MutableLiveData()
    private var color: HSVColor? = null

    val habitUpdate: LiveData<Habit> = mutableHabitUpdate
    val colorUpdate: LiveData<HSVColor> = mutableColorUpdate

    init {
        mutableHabitUpdate.value = habit
    }

    fun submit() {
        if(color != null)
            habit.color = color!!
        mutableHabitUpdate.postValue(habit)
    }

    fun updateName(name: String) {
        habit.name = name
    }

    fun updateDescription(description: String) {
        habit.description = description
    }

    fun updatePeriodic(periodic: String) {
        habit.periodic = periodic
    }

    fun updateHabitType(type: HabitType) {
        habit.type = type
    }

    fun updatePriority(priority: Priority) {
        habit.priority = priority
    }

    fun updateNumberRepeating(numberRepeating: Int) {
        habit.numberRepeating = numberRepeating
    }

    fun updateColor(color: HSVColor) {
        this.color = color
        mutableColorUpdate.postValue(this.color)
    }

    fun updateDateOfUpdate(date: Date) {
        habit.dateOfUpdate = date
    }
}