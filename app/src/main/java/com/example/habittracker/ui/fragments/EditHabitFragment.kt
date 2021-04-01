package com.example.habittracker.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.habittracker.Infrastructure.HSVColor
import com.example.habittracker.Infrastructure.firstOrNull
import com.example.habittracker.Models.Habit
import com.example.habittracker.Models.HabitType
import com.example.habittracker.Models.Priority
import com.example.habittracker.R
import kotlinx.android.synthetic.main.fragment_edit_habit.*

class EditHabitFragment : Fragment() {

    companion object {
        private val LOG_KEY = EditHabitFragment::class.simpleName
        private const val HABIT_KEY = "Habit Key"
        private const val COLOR_PICKER_KEY = "color picker Key!"
        private const val SELECT_COLOR_REQUEST = 0

        fun newInstance(habit: Habit): EditHabitFragment {
            return EditHabitFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(HABIT_KEY, habit)
                }
            }
        }
    }

    private lateinit var habit:Habit
    private var selectedColor: HSVColor = HSVColor()
    private lateinit var priorities: Map<String, Priority>
    private var callBack:IResultCallBack? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_habit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            habit = it.getSerializable(HABIT_KEY) as Habit
        }
        if(savedInstanceState == null) {
            if(arguments == null)
                throw IllegalArgumentException("Фрагмент был создан не через метод newInstance!")
            arguments?.let {
                habit = it.getSerializable(HABIT_KEY) as Habit
            }
        }
        else{
            habit = savedInstanceState.getSerializable(HABIT_KEY) as Habit
        }

        setInputsValue()
        submitButton.setOnClickListener { submitHabitData() }
        selectedColorView.setOnClickListener {
            val newInstance = ColorPicker.newInstance(16, selectedColor, tag)
            newInstance.setTargetFragment(this, SELECT_COLOR_REQUEST)
            newInstance.show(parentFragmentManager, COLOR_PICKER_KEY)
        }
    }

    private fun setInputsValue() {
        setPriority()
        nameInput.setText(habit.name)
        descriptionInput.setText(habit.description)
        periodicityInput.setText(habit.periodic)
        numberRepetitionsInput.setText(habit.numberRepeating.toString())
        habitTypeRadioGroup.check(getRadioButtonIdFromHabitType(habit.type))
        selectedColor = habit.color
        selectedColorView.setColorFilter(selectedColor.toArgbColor())
    }


    private fun setPriority(){
        val context = requireContext()
        priorities = Priority.valuesToLocalizationStrings(context)
        val prioritiesStrings = priorities.keys.toTypedArray()
        prioritySpinner.adapter = ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item, prioritiesStrings)
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
        setHabitValues()
        callBack?.setResult(habit)
    }

    private fun setHabitValues() {
        habit.name = nameInput.text.toString()
        habit.description = descriptionInput.text.toString()
        habit.periodic = periodicityInput.text.toString()
        habit.type = getHabitTypeFromRadioId(habitTypeRadioGroup.checkedRadioButtonId)
        val selectedItem = prioritySpinner.selectedItem as String
        habit.priority = priorities[selectedItem]!!
        habit.numberRepeating = numberRepetitionsInput.text.toString().toInt()
        habit.color = selectedColor
    }

    private fun getHabitTypeFromRadioId(checkedRadioButtonId: Int): HabitType {
        return when(checkedRadioButtonId){
            R.id.goodHabitRadioButton -> HabitType.GOOD
            R.id.badHabitRadioButton -> HabitType.BAD
            else -> throw NotImplementedError("")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(LOG_KEY, "requestCode = $requestCode, resultCode = $resultCode")
        when(resultCode)
        {
            AppCompatActivity.RESULT_OK -> {
                when(requestCode) {
                    SELECT_COLOR_REQUEST -> {
                        val color = ColorPicker.fromResult(data!!)
                        selectedColor = color
                        selectedColorView.setColorFilter(selectedColor.toArgbColor())
                    }
                }
            }
            AppCompatActivity.RESULT_CANCELED -> Log.e(LOG_KEY, "Canceled requestCode = $requestCode")
            else -> Log.e(LOG_KEY, "requestCode = $requestCode, resultCode = $resultCode")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        setHabitValues()
        outState.putSerializable(HABIT_KEY, habit)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            callBack = context as IResultCallBack
        }
        catch (e:Exception){
            throw IllegalArgumentException("Активити ${context::class.qualifiedName} не реадизует интерфейс ${IResultCallBack::class.qualifiedName}")
        }
    }

    override fun onDetach() {
        super.onDetach()
        callBack = null
    }

    interface IResultCallBack{
        fun setResult(habit: Habit)
    }
}