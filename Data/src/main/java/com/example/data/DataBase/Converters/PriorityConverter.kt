package com.example.data.DataBase.Converters

import androidx.room.TypeConverter
import com.example.domain.Models.Priority

class PriorityConverter {
    @TypeConverter
    fun toHabitType(value: Int) = enumValues<Priority>()[value]

    @TypeConverter
    fun fromHabitType(value: Priority) = value.ordinal
}