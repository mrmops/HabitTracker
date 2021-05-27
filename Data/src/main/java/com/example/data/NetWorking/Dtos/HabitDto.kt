package com.example.data.NetWorking.Dtos

import com.example.androidhelper.Infostructure.toAndroidColor
import com.example.androidhelper.Infostructure.toHSVColor
import com.example.data.Models.RemoteHabit
import com.example.domain.Infrastructure.HSVColor
import com.example.domain.Models.HabitType
import com.example.domain.Models.Priority
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

class HabitDto(
    @SerializedName("uid") val serverId: UUID?,
    @SerializedName("date") val dateOfUpdate: Date,
    @SerializedName("done_dates") val doneDates: MutableList<Date>,
    @SerializedName("title") val name: String?,
    @SerializedName("description") val description: String,
    @SerializedName("type") val type: HabitType,
    @SerializedName("priority") val priority: Priority,
    @SerializedName("frequency") val periodic: Int,
    @SerializedName("count") val numberRepeating: Int,
    @SerializedName("color") val color: Int
) {


    fun toHabit(): RemoteHabit = RemoteHabit(name, description, type, priority, periodic, numberRepeating, doneDates, color.toHSVColor())
        .let {
            it.serverId = serverId ?: UUID.randomUUID()
            it.dateOfUpdate = dateOfUpdate
            return it
        }

}

fun RemoteHabit.toDto(): HabitDto = HabitDto(
    if (this.uploadOnServer) this.serverId else null,
    this.dateOfUpdate ?: Date(),
    ArrayList(),
    if(this.name.isNullOrEmpty()) " " else this.name!!,
    if(this.description.isNullOrEmpty()) " " else this.description!!,
    this.type,
    this.priority,
    this.periodic,
    this.numberRepeating,
this.color?.toAndroidColor() ?: HSVColor().toAndroidColor())
