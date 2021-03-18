package com.example.habittracker.Infrastructure

import android.graphics.Color
import java.io.Serializable
import java.lang.IndexOutOfBoundsException

class HSVColor: Serializable {
    var Hue:Float = 0f
        set(value){
            if(value < 0 || value > 360)
                throw IndexOutOfBoundsException("Значение должно быть вдиапозоне [0, 360]")
            field = value
        }
    var saturation:Float = 1f
        set(value){
            if(value < 0 || value > 1)
                throw IndexOutOfBoundsException("Значение должно быть вдиапозоне [0, 100]")
            field = value
        }
    var brightness:Float = 1f
        set(value){
            if(value < 0 || value > 1)
                throw IndexOutOfBoundsException("Значение должно быть вдиапозоне [0, 100]")
            field = value
        }

    fun toArgbColor(): Int {
        return Color.HSVToColor(floatArrayOf(Hue, saturation, brightness))
    }
}