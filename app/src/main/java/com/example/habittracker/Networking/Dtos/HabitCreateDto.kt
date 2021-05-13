/*
package com.example.habittracker.Networking.Dtos

import com.example.habittracker.Models.Habit
import com.example.habittracker.Models.HabitType
import com.example.habittracker.Models.Priority
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class HabitCreateDto(
    @SerializedName("date") val dateOfUpdate: Date,
    @SerializedName("done_dates") val doneDates: List<Date>,
    @SerializedName("title") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("type") val type: HabitType,
    @SerializedName("priority") val priority: Priority,
    @SerializedName("frequency") val periodic: Int,
    @SerializedName("count") val numberRepeating: Int,
    @SerializedName("color") val color: Int
): Serializable

fun Habit.toCreateDto(): HabitCreateDto = HabitCreateDto(
    this.dateOfUpdate ?: Date(),
    ArrayList(),
    if(this.name.isNullOrEmpty()) " " else this.name!!,
    if(this.description.isNullOrEmpty()) " " else this.description!!,
    this.type,
    this.priority,
    this.periodic ?: 0,
    this.numberRepeating ?: 0,
    this.color?.toArgbColor() ?: 0 )*/