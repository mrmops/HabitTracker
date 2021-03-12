package com.example.habittracker.Models

import android.content.Context
import com.example.habittracker.R

enum class HabitType {
    GOOD,
    BAD;

    fun toStringResourceId(): Int{
        return when(this){
            BAD -> R.string.bad_habit_type
            GOOD -> R.string.good_habit_type
        }
    }

    fun toString(context: Context): String{
        return context.getString(toStringResourceId())
    }
}