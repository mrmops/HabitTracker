package com.example.data.Models

import com.example.domain.Models.Habit

class RemoteHabitMapper: Mapper<RemoteHabit, Habit>() {

    override fun map(habit: RemoteHabit): Habit = Habit(
        habit.name,
        habit.description,
        habit.type,
        habit.priority,
        habit.periodic,
        habit.numberRepeating,
        habit.color
    ).apply {
        serverId = habit.serverId
        uploadOnServer = habit.uploadOnServer
        wasUpdatedLocal = habit.wasUpdatedLocal
        dateOfUpdate = habit.dateOfUpdate
    }

    override fun reverseMap(habit: Habit): RemoteHabit {
        return RemoteHabit(
            habit.name,
            habit.description,
            habit.type,
            habit.priority,
            habit.periodic,
            habit.numberRepeating,
            habit.color
        ).apply {
            serverId = habit.serverId
            uploadOnServer = habit.uploadOnServer
            wasUpdatedLocal = habit.wasUpdatedLocal
            dateOfUpdate = habit.dateOfUpdate
        }
    }
}
