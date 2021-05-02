package com.example.habittracker.Infrastructure

import androidx.lifecycle.LiveData
import com.example.habittracker.DataBase.HabitDao
import com.example.habittracker.Models.Habit

fun <K, V> Map<K, V>.firstOrNull(filter: (Pair<K,V>) -> Boolean): Pair<K,V>? {
    for (item in this){
        val pair = item.toPair()
        if(filter(pair)){
            return pair
        }
    }
    return null
}
