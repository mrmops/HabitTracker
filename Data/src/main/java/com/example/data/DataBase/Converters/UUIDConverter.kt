package com.example.data.DataBase.Converters

import androidx.room.TypeConverter
import java.util.*

class UUIDConverter {

    @TypeConverter
    fun fromUUID(id: UUID): String = id.toString()

    @TypeConverter
    fun toUUID(str: String): UUID = UUID.fromString(str)
}