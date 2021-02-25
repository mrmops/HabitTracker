package com.example.habittracker

import android.content.Context

class Habit(
     var name:String,
     var description:String,
     var type: HabitType,
     var priority:Priority,
     var periodic:String){
}

enum class Priority {
    HIGH,
    NORMAL,
    SMALL;

    fun toResourceId(): Int{
        return when(this){
            HIGH -> R.string.high_priority
            NORMAL -> R.string.normal_priority
            SMALL -> R.string.small_priority
        }
    }
}

enum class HabitType {
    TYPE
}
