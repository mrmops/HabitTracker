package com.example.habittracker.Models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.habittracker.DataBase.Converters.DateConverter
import com.example.habittracker.DataBase.Converters.HabitTypeConverter
import com.example.habittracker.DataBase.Converters.PriorityConverter
import com.example.habittracker.Infrastructure.HSVColor
import java.io.Serializable
import java.util.*

@Entity
@TypeConverters(DateConverter::class, HabitTypeConverter::class, PriorityConverter::class)
data class Habit(
    var name:String? = null,
    var description:String?  = null,
    var type: HabitType = HabitType.GOOD,
    var priority: Priority = Priority.NORMAL,
    var periodic:String?  = null,
    var numberRepeating: Int? = 0,
    @Embedded var color: HSVColor? = null
): Serializable{

    @PrimaryKey(autoGenerate = true)
    var id:Long? = null
    var dateOfUpdate:Date? = null

    override fun equals(other: Any?): Boolean {
        return other != null && other is Habit && other.id == id
    }
}

