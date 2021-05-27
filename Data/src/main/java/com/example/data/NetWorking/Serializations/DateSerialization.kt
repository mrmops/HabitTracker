package com.example.data.NetWorking.Serializations

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.util.*

class DateSerialization : TypeAdapter<Date>() {
    override fun write(out: JsonWriter?, value: Date?) {
        out?.value(value?.time ?: 0)
    }

    override fun read(`in`: JsonReader?): Date = Date(`in`?.nextLong() ?: 0)
}