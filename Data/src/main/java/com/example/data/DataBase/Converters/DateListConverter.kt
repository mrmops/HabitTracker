package com.example.data.DataBase.Converters

import androidx.room.TypeConverter
import java.util.*

class DateListConverter {

    @TypeConverter
    fun toHabitType(value: String): List<Date> =
        value.split(",")
            .filter { it.isNotEmpty() }
            .map { Date(it.toLong()) }

    @TypeConverter
    fun fromHabitType(value: List<Date>):String =
        value.joinToString(separator = ",") {  it.time.toString() }
}