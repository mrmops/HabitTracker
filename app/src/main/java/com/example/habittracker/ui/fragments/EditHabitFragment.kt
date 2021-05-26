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
import com.example.habittracker.Infostructure.priorityValuesToLocalizationStrings
import com.example.androidhelper.Infostructure.toAndroidColor
import com.example.domain.Infrastructure.HSVColor
import com.example.domain.Infrastructure.firstOrNull
import com.example.domain.Models.Habit
import com.example.domain.Models.HabitType
import com.example.domain.Models.Priority
import com.example.habittracker.HabitApplication
import com.example.habittracker.R
import com.example.habittracker.ui.fragments.viewModels.HabitViewModel
import kotlinx.android.synthetic.main.fragment_edit_habit.*

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
    val args: EditHabitFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val habitRepo = (requireActivity().application as HabitApplication).applicationComponent
            .getHabitRepository()

        habitViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HabitViewModel(args.habit, habitRepo) as T
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
        habitViewModel.colorUpdate.observe(
            viewLifecycleOwner,
            { color -> selectedColorView.setColorFilter(color.toAndroidColor()) })
        setInputsValue(args.habit)
        setDefaultValues()
        submitButton.setOnClickListener { submitHabitData() }
        selectedColorView.setOnClickListener {
            val newInstance = ColorPicker.newInstance(16)
            newInstance.show(childFragmentManager, COLOR_PICKER_KEY)
        }
    }

    private fun setDefaultValues() {
        val context = requireContext()
        priorities = priorityValuesToLocalizationStrings(context)
        val prioritiesStrings = priorities.keys.toTypedArray()
        prioritySpinner.adapter =
            ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, prioritiesStrings)
    }

    private fun setInputsValue(habit: Habit) {
        setPriority(habit)
        nameInput.setText(habit.name)
        descriptionInput.setText(habit.description)
        periodicityInput.setText(habit.periodic?.toString())
        numberRepetitionsInput.setText(habit.numberRepeating.toString())
        habitTypeRadioGroup.check(getRadioButtonIdFromHabitType(habit.type))
        selectedColorView.setColorFilter(habit.color?.toAndroidColor() ?: HSVColor().toAndroidColor())
    }


    private fun setPriority(habit: Habit) {
        val context = requireContext()
        priorities = priorityValuesToLocalizationStrings(context)
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
        if (habit != null)
            callBack?.onFinishEditFragment()

    }

    private fun setHabitValues() {
        val periodicStr = periodicityInput.text.toString()
        val selectedItem = prioritySpinner.selectedItem as String
        val numberOfRepeatingToString = numberRepetitionsInput.text.toString()

        habitViewModel.submit(
            nameInput.text.toString(),
            descriptionInput.text.toString(),
            if (periodicStr.isNotEmpty()) periodicStr.toInt() else 0,
            getHabitTypeFromRadioId(habitTypeRadioGroup.checkedRadioButtonId),
            priorities[selectedItem]!!,
            if (numberOfRepeatingToString.isEmpty()) 0 else numberOfRepeatingToString.toInt()
        )
        habitViewModel.saveHabit()
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
        fun onFinishEditFragment()
    }
}