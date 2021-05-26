package com.example.domain.Models

import com.example.domain.Infrastructure.HSVColor
import java.io.Serializable
import java.util.*


data class Habit(
    var name: String? = null,
    var description: String? = null,
    var type: HabitType = HabitType.GOOD,
    var priority: Priority = Priority.NORMAL,
    var periodic: Int? = null,
    var numberRepeating: Int? = 0,
    var color: HSVColor? = null
) : Serializable {

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

