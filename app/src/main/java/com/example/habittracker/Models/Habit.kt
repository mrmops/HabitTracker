package com.example.habittracker.Models

import androidx.room.*
import com.example.habittracker.DataBase.Converters.DateConverter
import com.example.habittracker.DataBase.Converters.HabitTypeConverter
import com.example.habittracker.DataBase.Converters.PriorityConverter
import com.example.habittracker.DataBase.Converters.UUIDConverter
import com.example.habittracker.Infrastructure.HSVColor
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import java.util.*

@Entity
@TypeConverters(
    DateConverter::class,
    HabitTypeConverter::class,
    PriorityConverter::class,
    UUIDConverter::class
)
data class Habit(
    var name: String? = null,
    var description: String? = null,
    var type: HabitType = HabitType.GOOD,
    var priority: Priority = Priority.NORMAL,
    var periodic: Int? = null,
    var numberRepeating: Int? = 0,
    @Embedded var color: HSVColor? = null
) : Serializable {

    @PrimaryKey
    @TypeConverters(UUIDConverter::class)
    var serverId: UUID = UUID.randomUUID()

    var uploadOnServer = false
    var wasUpdatedLocal = true

    var dateOfUpdate: Date? = null

    override fun equals(other: Any?): Boolean {
        return other != null && other is Habit && other.serverId == serverId
    }

    override fun hashCode(): Int {
        return serverId.hashCode()
    }
}

