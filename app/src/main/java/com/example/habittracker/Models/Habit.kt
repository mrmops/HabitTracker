package com.example.habittracker.Models

import com.example.habittracker.Infrastructure.HSVColor
import java.io.Serializable
import java.time.LocalDate
import java.util.*

class Habit: Serializable{

    lateinit var id:UUID
    lateinit var dateOfUpdate:Date
    var name:String = ""
    var description:String = ""
    var type: HabitType = HabitType.GOOD
    var priority: Priority = Priority.NORMAL
    var periodic:String = ""
    var numberRepeating: Int = 0
    var color: HSVColor = HSVColor()


    override fun equals(other: Any?): Boolean {
        return other != null && other is Habit && other.id == id
    }
}

