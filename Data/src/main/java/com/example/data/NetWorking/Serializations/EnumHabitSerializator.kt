package com.example.data.NetWorking.Serializations

import com.example.domain.Models.HabitType
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class HabitTypeSerialization: TypeAdapter<HabitType>(){
    override fun write(out: JsonWriter?, value: HabitType?) {
        out?.value(HabitType.values().indexOf(value!!))
    }

    override fun read(`in`: JsonReader?): HabitType = HabitType.values()[`in`?.nextInt() ?: 0]

}

