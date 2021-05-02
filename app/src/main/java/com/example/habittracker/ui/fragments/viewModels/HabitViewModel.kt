package com.example.habittracker.ui.fragments.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.DataBase.HabitDao
import com.example.habittracker.Infrastructure.HSVColor
import com.example.habittracker.Models.Habit
import com.example.habittracker.Models.HabitType
import com.example.habittracker.Models.Priority
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class HabitViewModel(private val habit: Habit, private val habitDao: HabitDao) : ViewModel(),
    CoroutineScope {
    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    private val mutableHabitUpdate: MutableLiveData<Habit> = MutableLiveData()
    private val mutableColorUpdate: MutableLiveData<HSVColor> = MutableLiveData()

    var name: String? = habit.name
    var color: HSVColor? = habit.color
    var description: String? = habit.description
    var periodic: String? = habit.periodic
    var type: HabitType? = habit.type
    var priority: Priority? = habit.priority
    var numberRepeating: Int? = habit.numberRepeating
    var dateOfUpdate: Date? = habit.dateOfUpdate

    val habitUpdate: LiveData<Habit> = mutableHabitUpdate
    val colorUpdate: LiveData<HSVColor> = mutableColorUpdate

    init {
        mutableHabitUpdate.postValue(habit)
    }

    fun submitAndSaveToDbAsync() = launch(Dispatchers.Main) {
        withContext(Dispatchers.IO) {
            submit()
            saveToDbAsync()
        }
    }

    fun submit() {
        if (name != null)
            habit.name = name!!
        if (color != null)
            habit.color = color!!
        if (description != null)
            habit.description = description!!
        if (periodic != null)
            habit.periodic = periodic!!
        if (type != null)
            habit.type = type!!
        if (priority != null)
            habit.priority = priority!!
        if (numberRepeating != null)
            habit.numberRepeating = numberRepeating!!
        if (dateOfUpdate != null)
            habit.dateOfUpdate = dateOfUpdate!!
        mutableHabitUpdate.postValue(habit)
    }

    private suspend fun saveToDbAsync() {
        habitDao.insertHabit(habit)
    }

    fun updateColor(color: HSVColor) {
        this.color = color
        mutableColorUpdate.postValue(this.color)
    }

    override fun onCleared() {
        super.onCleared()
        //coroutineContext.cancelChildren()
    }
}