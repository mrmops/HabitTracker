package com.example.habittracker.DataBase

import androidx.lifecycle.LiveData
import com.example.habittracker.Models.Habit

fun HabitDao.filterAndSortHabitsByDate(filter: String?, revertSort: Boolean): LiveData<List<Habit>> =
    if(!filter.isNullOrEmpty())
        if(!revertSort)
            this.getFilteredAndForwardOrderHabits(filter)
        else
            this.getFilteredAndBackwardOrderHabits(filter)
    else
        if(!revertSort)
            this.getAllHabitsACSOrderByDate()
        else
            this.getAllHabitsDESCOrderByDate()