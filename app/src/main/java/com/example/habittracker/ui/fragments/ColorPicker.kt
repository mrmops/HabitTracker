package com.example.habittracker.ui.fragments

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.Infrastructure.HSVColor
import com.example.habittracker.Infrastructure.HSVColorGradientGenerator
import com.example.habittracker.R
import com.example.habittracker.ui.fragments.viewModels.HabitViewModel
import kotlinx.android.synthetic.main.fragment_color_picker.*
import kotlinx.android.synthetic.main.fragment_color_picker.selectedColorView

class ColorPicker : DialogFragment() {
    companion object {
        private val LOG_KEY = ColorPicker::class.java.simpleName
        private const val COLORS_COUNT_KEY = "Colors count key"
        private const val COLOR_KEY = "Color key"

        fun newInstance(colorCount: Int): ColorPicker {
            val fragment = ColorPicker()
            val apply = Bundle().apply {
                putInt(COLORS_COUNT_KEY, colorCount)
            }
            fragment.arguments = apply
            return fragment
        }
    }

    private lateinit var habitViewModel: HabitViewModel
    private lateinit var selectedColor: HSVColor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        habitViewModel = ViewModelProvider(requireActivity()).get(HabitViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_color_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        colorPicker.background = HSVColorGradientGenerator
            .generateGradient(
                GradientDrawable.Orientation.LEFT_RIGHT,
                GradientDrawable.LINEAR_GRADIENT
            )

        setBackground()

        val color: HSVColor =
            if (savedInstanceState == null)
                if (habitViewModel.habitUpdate.value?.color == null) {
                    Log.e(LOG_KEY, "$habitViewModel no has habit value or color!")
                    HSVColor()
                } else habitViewModel.habitUpdate.value?.color!!
            else
                savedInstanceState.getSerializable(COLOR_KEY) as HSVColor

        setSelectedColor(color)

        submitButton.setOnClickListener {
            habitViewModel.updateColor(selectedColor)
            habitViewModel.submit()
            dismiss()
        }
    }

    private fun setBackground() {
        val inflater = LayoutInflater.from(requireContext())
        val colorsCount = requireArguments().getInt(COLORS_COUNT_KEY)

        val imageViewSize = resources.getDimensionPixelSize(R.dimen.square_size)
        val margin = imageViewSize / 3
        val imageViewFullWidth = imageViewSize + margin * 2
        val totalPickerWidth = imageViewFullWidth * colorsCount

        for (i in 0 until colorsCount) {
            val imageView =
                inflater.inflate(R.layout.color_picker_item, colorPicker, false) as ImageView
            val layoutParams: LinearLayout.LayoutParams =
                imageView.layoutParams as LinearLayout.LayoutParams
            layoutParams.setMargins(margin, margin, margin, margin)
            val shiftImageViewCenter = imageViewFullWidth * i + imageViewFullWidth / 2
            val colorHue = 360 * shiftImageViewCenter.toFloat() / totalPickerWidth

            val color = HSVColor().apply { Hue = colorHue }
            imageView.setColorFilter(color.toArgbColor())
            imageView.setOnClickListener {
                setSelectedColor(color)
            }
            colorPicker.addView(imageView)
        }
        colorPicker.invalidate()
    }

    private fun setSelectedColor(color: HSVColor) {
        selectedColor = color
        selectedColorView.setColorFilter(selectedColor.toArgbColor())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(COLOR_KEY, selectedColor)
    }
}