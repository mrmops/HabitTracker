package com.example.habittracker.DataBase

import androidx.lifecycle.LiveData
import com.example.habittracker.Models.Habit

fun HabitDao.filterAndSortHabitsByDate(filter: String?, revertSort: Boolean): LiveData<List<Habit>> =
    if(!filter.isNullOrEmpty())
        if(!revertSort)
            this.getFilteredAndForwardSortHabitsByDate(filter)
        else
            this.getFilteredAndBackwardSortHabitsByDate(filter)
    else
        if(!revertSort)
            this.getAllHabitsForwarOrderByDate()
        else
            this.getAllHabitsBackwardOrderByDate()