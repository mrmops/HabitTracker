package com.example.habittracker.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import com.example.habittracker.Infrastructure.HSVColor
import com.example.habittracker.Infrastructure.HSVColorGradientGenerator
import com.example.habittracker.R
import kotlinx.android.synthetic.main.activity_habit_edit.*
import kotlinx.android.synthetic.main.fragment_color_picker.*
import kotlinx.android.synthetic.main.fragment_color_picker.selectedColorView

class ColorPicker : DialogFragment() {
    companion object{

        private const val COLORS_COUNT_KEY = "Colors count key"
        private const val COLOR_KEY = "Color key"
        private const val PARENT_TAG_KEY = "Parent Tag key"

        fun newInstance(colorCount:Int, parentTag: String?): ColorPicker {
            val fragment =ColorPicker()
            val apply = Bundle().apply {
                putInt(COLORS_COUNT_KEY, colorCount)
                putString(PARENT_TAG_KEY, parentTag)
                putSerializable(COLOR_KEY, HSVColor())
            }
            fragment.arguments = apply
            return fragment
        }

        fun newInstance(colorCount:Int, selectedColor:HSVColor, parentTag: String?): ColorPicker {
            val fragment =ColorPicker()
            val apply = Bundle().apply {
                putInt(COLORS_COUNT_KEY, colorCount)
                putString(PARENT_TAG_KEY, parentTag)
                putSerializable(COLOR_KEY, selectedColor)
            }
            fragment.arguments = apply
            return fragment
        }

        fun fromResult(intent: Intent):HSVColor{
            return intent.getSerializableExtra(COLOR_KEY) as HSVColor
        }
    }

    private lateinit var selectedColor: HSVColor
    private var parentTag:String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_color_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inflater = LayoutInflater.from(requireContext())
        colorPicker.background = HSVColorGradientGenerator
            .generateGradient(
                GradientDrawable.Orientation.LEFT_RIGHT,
                GradientDrawable.LINEAR_GRADIENT)

        val colorsCount = requireArguments().getInt(COLORS_COUNT_KEY)
        parentTag = requireArguments().getString(PARENT_TAG_KEY)


        val imageViewSize = resources.getDimensionPixelSize(R.dimen.square_size)
        val margin = imageViewSize / 3
        val imageViewFullWidth = imageViewSize + margin * 2
        val totalPickerWidth = imageViewFullWidth * colorsCount

        for (i in 0 until colorsCount){
            val imageView  = inflater.inflate(R.layout.color_picker_item, colorPicker, false) as ImageView
            val layoutParams: LinearLayout.LayoutParams = imageView.layoutParams as LinearLayout.LayoutParams
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

        val color = if(savedInstanceState == null)
            requireArguments().getSerializable(COLOR_KEY) as HSVColor
        else
            savedInstanceState.getSerializable(COLOR_KEY) as HSVColor
        setSelectedColor(color)

        submitButton.setOnClickListener {
            val parentFragment = parentFragmentManager.findFragmentByTag(parentTag)
            parentFragment?.onActivityResult(targetRequestCode, AppCompatActivity.RESULT_OK,
                Intent().apply { putExtra(COLOR_KEY, selectedColor) })
            dismiss()
        }
    }

    private fun setSelectedColor(color: HSVColor){
        selectedColor = color
        selectedColorView.setColorFilter(selectedColor.toArgbColor())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(COLOR_KEY, selectedColor)
    }
}