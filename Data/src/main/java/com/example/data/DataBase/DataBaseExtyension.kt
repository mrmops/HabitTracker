package com.example.data.DataBase

import com.example.data.Models.RemoteHabit
import kotlinx.coroutines.flow.Flow

fun HabitDao.filterAndSortHabitsByDate(filter: String?, revertSort: Boolean): Flow<List<RemoteHabit>> =
    if(!filter.isNullOrEmpty())
        if(!revertSort)
            this.getFilteredAndForwardSortHabitsByDate(filter)
        else
            this.getFilteredAndBackwardSortHabitsByDate(filter)
    else
        if(!revertSort)
            this.getAllHabitsForwardOrderByDate()
        else
            this.getAllHabitsBackwardOrderByDate()