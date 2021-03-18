package com.example.habittracker

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.example.habittracker.Infrastructure.HSVColor
import com.example.habittracker.Infrastructure.HSVColorGradientGenerator
import com.example.habittracker.Infrastructure.firstOrNull
import com.example.habittracker.Models.Habit
import com.example.habittracker.Models.HabitType
import com.example.habittracker.Models.Priority
import kotlinx.android.synthetic.main.activity_habit_edit.*

class HabitEditActivity : AppCompatActivity() {
    companion object{
        private val LOG_KEY = HabitEditActivity::class.simpleName
        private const val HABIT_KEY = "Habit Key!"

        fun newIntent(context: Context, habit: Habit): Intent{
            return Intent(context, HabitEditActivity::class.java).apply {
                putExtra(HABIT_KEY, habit)
            }
        }

        fun fromResult(data:Intent): Habit{
            return data.getSerializableExtra(HABIT_KEY) as Habit
        }
    }

    private lateinit var habit: Habit
    private var selectedColor:HSVColor = HSVColor()

    private lateinit var priorities: Map<String, Priority>

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_KEY, "OnCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_edit)
        Log.d(LOG_KEY, "setContentView ended")
        val extras = intent.extras ?: throw IllegalArgumentException()
        habit = extras.getSerializable(HABIT_KEY) as Habit
        setInputsValue()
        submitButton.setOnClickListener { submitHabitData() }
        setUpGradientColorPicker()
        Log.d(LOG_KEY, "OnCreate ended")
    }

    private fun setUpGradientColorPicker() {
        Log.d(LOG_KEY, "Start set up gradient")
        colorPicker.background = HSVColorGradientGenerator
                .generateGradient(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        GradientDrawable.LINEAR_GRADIENT)
        val imageViewSize = resources.getDimensionPixelSize(R.dimen.square_size)
        val margin = imageViewSize / 3
        val imageViewFullWidth = imageViewSize + margin * 2
        val totalPickerWidth = imageViewFullWidth * colorPicker.childCount
        var imageViewCounter = 0
        for(view in colorPicker.children){
            val imageView  = view as ImageView
            val layoutParams: LinearLayout.LayoutParams = imageView.layoutParams as LinearLayout.LayoutParams
            layoutParams.setMargins(margin, margin, margin, margin)
            val shiftImageViewCenter = imageViewFullWidth * imageViewCounter + imageViewFullWidth / 2
            val colorHue = 360 * shiftImageViewCenter.toFloat() / totalPickerWidth
            val color = HSVColor().apply { Hue = colorHue }
            imageView.setColorFilter(color.toArgbColor())
            imageView.setOnClickListener {
                selectedColor = color
                selectedColorView.setColorFilter(selectedColor.toArgbColor())
            }
            imageViewCounter++
        }
        Log.d(LOG_KEY, "Stop set up gradient")
    }

    private fun setInputsValue() {
        setPriority()
        nameInput.setText(habit.name)
        descriptionInput.setText(habit.description)
        periodicityInput.setText(habit.periodic)
        numberRepetitionsInput.setText(habit.numberRepeating.toString())
        habitTypeRadioGroup.check(getRadioButtonIdFromHabitType(habit.type))
        selectedColorView.setColorFilter(habit.color.toArgbColor())
    }


    private fun setPriority(){
        priorities = Priority.valuesToLocalizationStrings(this)
        val prioritiesStrings = priorities.keys.toTypedArray()
        prioritySpinner.adapter = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item, prioritiesStrings)
        val selectedPriorityIndex = prioritiesStrings.indexOf(priorities.firstOrNull { it.second == habit.priority }!!.first)
        prioritySpinner.setSelection(selectedPriorityIndex)
    }


    private fun getRadioButtonIdFromHabitType(type: HabitType): Int {
        return when(type){
            HabitType.GOOD -> R.id.goodHabitRadioButton
            HabitType.BAD -> R.id.badHabitRadioButton
        }
    }

    private fun submitHabitData(){
        Log.i(LOG_KEY, "Submit Habit data")
        habit.name = nameInput.text.toString()
        habit.description = descriptionInput.text.toString()
        habit.periodic = periodicityInput.text.toString()
        habit.type = getHabitTypeFromRadioId(habitTypeRadioGroup.checkedRadioButtonId)
        val selectedItem = prioritySpinner.selectedItem as String
        habit.priority = priorities[selectedItem]!!
        habit.numberRepeating = numberRepetitionsInput.text.toString().toInt()
        habit.color = selectedColor
        setResult(RESULT_OK, Intent().apply { putExtra(HABIT_KEY, habit) })
        finish()
    }

    private fun getHabitTypeFromRadioId(checkedRadioButtonId: Int): HabitType {
        return when(checkedRadioButtonId){
            R.id.goodHabitRadioButton -> HabitType.GOOD
            R.id.badHabitRadioButton -> HabitType.BAD
            else -> throw NotImplementedError("")
        }
    }
}

