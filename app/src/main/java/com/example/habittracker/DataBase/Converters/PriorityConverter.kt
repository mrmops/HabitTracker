package com.example.habittracker.DataBase.Converters

import androidx.room.TypeConverter
import com.example.habittracker.Models.Priority

class PriorityConverter {
    @TypeConverter
    fun toHabitType(value: Int) = enumValues<Priority>()[value]

    @TypeConverter
    fun fromHabitType(value: Priority) = value.ordinal
}