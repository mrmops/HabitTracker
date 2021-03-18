package com.example.habittracker.Infrastructure

import android.graphics.Color
import android.graphics.drawable.GradientDrawable

class HSVColorGradientGenerator {
    companion object{
        private var gradientColors: ArrayList<Int>? = null

        private fun generateGradientColors(): IntArray{
            if(gradientColors == null) {
                gradientColors = ArrayList<Int>()
                for (i in 0..360) {
                    gradientColors!!.add(HSVColor().apply { Hue = i.toFloat() }.toArgbColor())
                }
            }
            return gradientColors!!.toIntArray()
        }

        fun generateGradient(orientation: GradientDrawable.Orientation, gradientType: Int):GradientDrawable{
            return GradientDrawable(
                orientation,
                generateGradientColors()
            ).apply { this.gradientType = gradientType }
        }
    }
}