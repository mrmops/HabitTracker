package com.example.habittracker.Models

import android.content.Context
import com.example.habittracker.R

enum class Priority {

    HIGH,
    NORMAL,
    SMALL;

    companion object{
        fun localizationStrings(context: Context): Map<String, Priority>{
            val values = values()
            return values.map { Pair(context.getString(it.toStringResourceId()), it) }.toMap()
        }
    }

    fun toStringResourceId(): Int = when(this){
        HIGH -> R.string.high_priority
        NORMAL -> R.string.normal_priority
        SMALL -> R.string.small_priority
    }

    fun toString(context: Context): String = context.getString(toStringResourceId())
}