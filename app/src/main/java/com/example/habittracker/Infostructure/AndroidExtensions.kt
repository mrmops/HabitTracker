package com.example.habittracker.Infostructure

import android.content.Context
import com.example.domain.Models.HabitType
import com.example.domain.Models.Priority
import com.example.habittracker.R

fun HabitType.toStringResourceId(): Int{
    return when(this){
        HabitType.BAD -> R.string.bad_habit_type
        HabitType.GOOD -> R.string.good_habit_type
    }
}

fun HabitType.toString(context: Context): String{
    return context.getString(toStringResourceId())
}

fun priorityValuesToLocalizationStrings(context: Context): Map<String, Priority> =
    Priority.values().map { Pair(it.toString(context), it) }.toMap()


fun Priority.toStringResourceId(): Int = when(this){
    Priority.HIGH -> R.string.high_priority
    Priority.NORMAL -> R.string.normal_priority
    Priority.SMALL -> R.string.small_priority
}

fun Priority.toString(context: Context): String = context.getString(toStringResourceId())