package com.example.habittracker.Models

import java.io.Serializable
import java.util.*

class Habit: Serializable{

    lateinit var id:UUID
    var name:String = ""
    var description:String = ""
    var type: HabitType = HabitType.GOOD
    var priority: Priority = Priority.NORMAL
    var periodic:String = ""
    var numberRepeating: Int = 0


    override fun equals(other: Any?): Boolean {
        return other != null && other is Habit && other.id == id
    }
}

