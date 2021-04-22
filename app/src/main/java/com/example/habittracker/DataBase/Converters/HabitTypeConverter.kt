package com.example.habittracker.DataBase.Converters

import androidx.room.TypeConverter
import com.example.habittracker.Models.HabitType

class HabitTypeConverter {

    @TypeConverter
    fun toHabitType(value: Int) = enumValues<HabitType>()[value]

    @TypeConverter
    fun fromHabitType(value: HabitType) = value.ordinal
}

