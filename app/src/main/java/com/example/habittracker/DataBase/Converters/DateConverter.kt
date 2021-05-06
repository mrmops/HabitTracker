package com.example.habittracker.DataBase.Converters

import androidx.room.TypeConverter
import com.google.gson.*
import java.lang.reflect.Type
import java.util.*

class DateConverter /*: JsonSerializer<Date>, JsonDeserializer<Date>*/ {

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(time: Long): Date {
        return Date(time)
    }
}