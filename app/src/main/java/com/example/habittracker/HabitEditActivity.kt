package com.example.habittracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.habittracker.Models.Habit
import com.example.habittracker.Models.HabitType
import com.example.habittracker.Models.Priority
import kotlinx.android.synthetic.main.activity_habit_edit.*

class HabitEditActivity : AppCompatActivity() {
    companion object{

        private const val EDIT_MOD_FLAG_KEY = "Edit mod key!"
        const val HABIT_KEY = "Habit Key!"
        const val HABIT_POSITION = "Habit position key!"

        fun newIntent(context: Context, habit: Habit): Intent{
            return Intent(context, HabitEditActivity::class.java).apply {
                putExtra(EDIT_MOD_FLAG_KEY, true)
                putExtra(HABIT_KEY, habit)
            }
        }
    }

    private lateinit var habit: Habit

    private lateinit var priorities: Map<String, Priority>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_edit)
        val extras = intent.extras ?: throw IllegalArgumentException()
        habit = extras.getSerializable(HABIT_KEY) as Habit
        setInputsValue()
        submitButton.setOnClickListener { submitHabitData() }
    }

    private fun setInputsValue() {
        setPriority()
        nameInput.setText(habit.name)
        descriptionInput.setText(habit.description)
        periodicityInput.setText(habit.periodic)
        numberRepetitionsInput.setText(habit.numberRepeating.toString())
        habitTypeRadioGroup.check(getRadioButtonIdFromHabitType(habit.type))
    }


    private fun setPriority(){
        priorities = Priority.localizationStrings(this)
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
        habit.name = nameInput.text.toString()
        habit.description = descriptionInput.text.toString()
        habit.periodic = periodicityInput.text.toString()
        habit.type = getHabitTypeFromRadioId(habitTypeRadioGroup.checkedRadioButtonId)
        val selecteedItem = prioritySpinner.selectedItem as String
        habit.priority = priorities[selecteedItem]!!
        habit.numberRepeating = numberRepetitionsInput.text.toString().toInt()
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

private fun <K, V> Map<K, V>.firstOrNull(filter: (Pair<K,V>) -> Boolean): Pair<K,V>? {
    for (item in this){
        val pair = item.toPair()
        if(filter(pair)){
            return pair
        }
    }
    return null
}

