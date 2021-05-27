package com.example.androidhelper.Infostructure

import android.graphics.Color
import com.example.domain.Infrastructure.HSVColor

fun HSVColor.toAndroidColor(): Int{
    return Color.HSVToColor(floatArrayOf(this.Hue, this.saturation, this.brightness))
}

fun Int.toHSVColor(): HSVColor {
    val floatArrayOf = floatArrayOf(0.0f, 0.0f, 0.0f)
    Color.colorToHSV(this, floatArrayOf)
    return HSVColor().apply {
        Hue = floatArrayOf[0]
        saturation = floatArrayOf[1]
        brightness = floatArrayOf[2]
    }
}