package com.example.habittracker.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.habittracker.Infrastructure.firstOrNull
import com.example.habittracker.Models.Habit
import com.example.habittracker.Models.HabitType
import com.example.habittracker.Models.Priority
import com.example.habittracker.R
import com.example.habittracker.ui.fragments.viewModels.HabitViewModel
import kotlinx.android.synthetic.main.fragment_edit_habit.*
import java.util.*

class EditHabitFragment : Fragment() {

    companion object {
        private val LOG_KEY = EditHabitFragment::class.simpleName
        private const val COLOR_PICKER_KEY = "color picker Key!"

        fun newInstance(): EditHabitFragment {
            return EditHabitFragment()
        }
    }

    private lateinit var habitViewModel: HabitViewModel
    private lateinit var priorities: Map<String, Priority>
    private var callBack: IResultCallBack? = null
    val args : EditHabitFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        habitViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HabitViewModel(args.habit) as T
            }
        }).get(HabitViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_habit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        habitViewModel.colorUpdate.observe(viewLifecycleOwner, { color -> selectedColorView.setColorFilter(color.toArgbColor())})
        val habit = habitViewModel.habitUpdate.value
        if(habit != null)
            setInputsValue(habit)
        submitButton.setOnClickListener { submitHabitData() }
        selectedColorView.setOnClickListener {
            val newInstance = ColorPicker.newInstance(16)
            newInstance.show(childFragmentManager, COLOR_PICKER_KEY)
        }
    }

    private fun setInputsValue(habit: Habit) {
        setPriority(habit)
        nameInput.setText(habit.name)
        descriptionInput.setText(habit.description)
        periodicityInput.setText(habit.periodic)
        numberRepetitionsInput.setText(habit.numberRepeating.toString())
        habitTypeRadioGroup.check(getRadioButtonIdFromHabitType(habit.type))
        selectedColorView.setColorFilter(habit.color.toArgbColor())
    }


    private fun setPriority(habit: Habit) {
        val context = requireContext()
        priorities = Priority.valuesToLocalizationStrings(context)
        val prioritiesStrings = priorities.keys.toTypedArray()
        prioritySpinner.adapter =
            ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, prioritiesStrings)
        val selectedPriorityIndex =
            prioritiesStrings.indexOf(priorities.firstOrNull { it.second == habit.priority }!!.first)
        prioritySpinner.setSelection(selectedPriorityIndex)
    }


    private fun getRadioButtonIdFromHabitType(type: HabitType): Int {
        return when (type) {
            HabitType.GOOD -> R.id.goodHabitRadioButton
            HabitType.BAD -> R.id.badHabitRadioButton
        }
    }

    private fun submitHabitData() {
        Log.i(LOG_KEY, "Submit Habit data")
        setHabitValues()
        val habit = habitViewModel.habitUpdate.value
        if(habit != null) {
            callBack?.onHabitEdited(habit)
            callBack?.onFinishEditFragment()
        }
    }

    private fun setHabitValues() {
        habitViewModel.updateName(nameInput.text.toString())
        habitViewModel.updateDescription(descriptionInput.text.toString())
        habitViewModel.updatePeriodic(periodicityInput.text.toString())
        habitViewModel.updateHabitType(getHabitTypeFromRadioId(habitTypeRadioGroup.checkedRadioButtonId))
        val selectedItem = prioritySpinner.selectedItem as String
        habitViewModel.updatePriority(priorities[selectedItem]!!)
        habitViewModel.updateNumberRepeating(numberRepetitionsInput.text.toString().toInt())
        habitViewModel.updateDateOfUpdate(Date())
        habitViewModel.submit()
    }

    private fun getHabitTypeFromRadioId(checkedRadioButtonId: Int): HabitType {
        return when (checkedRadioButtonId) {
            R.id.goodHabitRadioButton -> HabitType.GOOD
            R.id.badHabitRadioButton -> HabitType.BAD
            else -> throw NotImplementedError("")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        setHabitValues()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            callBack = context as IResultCallBack
        } catch (e: Exception) {
            throw IllegalArgumentException("Активити ${context::class.qualifiedName} не реализует интерфейс ${IResultCallBack::class.qualifiedName}")
        }
    }

    override fun onDetach() {
        super.onDetach()
        callBack = null
    }

    interface IResultCallBack {
        fun onHabitEdited(habit: Habit)
        fun onFinishEditFragment()
    }
}