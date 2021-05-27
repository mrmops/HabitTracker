package com.example.data.Models

import androidx.room.*
import com.example.data.DataBase.Converters.*
import com.example.domain.Infrastructure.HSVColor
import com.example.domain.Models.HabitType
import com.example.domain.Models.Priority
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

@Entity
@TypeConverters(
    DateConverter::class,
    HabitTypeConverter::class,
    PriorityConverter::class,
    UUIDConverter::class,
    DateListConverter::class
)
data class RemoteHabit(
    var name: String? = null,
    var description: String? = null,
    var type: HabitType = HabitType.GOOD,
    var priority: Priority = Priority.NORMAL,
    var periodic: Int = 1,
    var numberRepeating: Int = 0,
    val doneDates: MutableList<Date> = ArrayList(),
    @Embedded var color: HSVColor? = null
) : Serializable {

    @PrimaryKey
    @TypeConverters(UUIDConverter::class)
    var serverId: UUID = UUID.randomUUID()

    var uploadOnServer = false
    var wasUpdatedLocal = true

    var dateOfUpdate: Date? = null

    override fun equals(other: Any?): Boolean {
        return other != null && other is RemoteHabit && other.serverId == serverId
    }

    override fun hashCode(): Int {
        return serverId.hashCode()
    }
}

