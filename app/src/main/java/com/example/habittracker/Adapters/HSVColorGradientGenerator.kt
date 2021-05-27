package com.example.habittracker.Adapters

import android.graphics.drawable.GradientDrawable
import com.example.domain.Infrastructure.HSVColor
import com.example.androidhelper.Infostructure.toAndroidColor

class HSVColorGradientGenerator {
    companion object{
        private var gradientColors: ArrayList<Int>? = null

        private fun generateGradientColors(): IntArray{
            if(gradientColors == null) {
                gradientColors = ArrayList<Int>()
                for (i in 0..360) {
                    gradientColors!!.add(HSVColor().apply { Hue = i.toFloat() }.toAndroidColor())
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