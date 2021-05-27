package com.example.data.Models

import com.example.domain.Models.Habit

class RemoteHabitMapper: Mapper<RemoteHabit, Habit>() {

    override fun map(obj: RemoteHabit): Habit = Habit(
        obj.name,
        obj.description,
        obj.type,
        obj.priority,
        obj.periodic,
        obj.numberRepeating,
        obj.doneDates,
        obj.color
    ).apply {
        serverId = obj.serverId
        uploadOnServer = obj.uploadOnServer
        wasUpdatedLocal = obj.wasUpdatedLocal
        dateOfUpdate = obj.dateOfUpdate
    }

    override fun reverseMap(obj: Habit): RemoteHabit {
        return RemoteHabit(
            obj.name,
            obj.description,
            obj.type,
            obj.priority,
            obj.periodic,
            obj.numberRepeating,
            obj.doneDates,
            obj.color
        ).apply {
            serverId = obj.serverId
            uploadOnServer = obj.uploadOnServer
            wasUpdatedLocal = obj.wasUpdatedLocal
            dateOfUpdate = obj.dateOfUpdate
        }
    }
}
