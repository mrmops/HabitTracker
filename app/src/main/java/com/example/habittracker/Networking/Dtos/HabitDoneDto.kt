package com.example.habittracker.Networking.Dtos

import com.google.gson.annotations.SerializedName
import java.util.*

class HabitDoneDto(
    @SerializedName("date") val dateOfDone: Date,
    @SerializedName("habit_uid") val habitId: UUID
)
