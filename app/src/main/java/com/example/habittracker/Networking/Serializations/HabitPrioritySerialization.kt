package com.example.habittracker.Networking.Serializations

import com.example.habittracker.Models.Priority
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class HabitPrioritySerialization: TypeAdapter<Priority>(){
    override fun write(out: JsonWriter?, value: Priority?) {
        out?.value(Priority.values().indexOf(value!!))
    }

    override fun read(`in`: JsonReader?): Priority = Priority.values()[`in`?.nextInt() ?: 0]
}
