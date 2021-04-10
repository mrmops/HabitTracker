package com.example.habittracker.Adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.habittracker.Models.Habit
import com.example.habittracker.Models.HabitType
import com.example.habittracker.R
import com.example.habittracker.ui.fragments.ListHabitFragment
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HabitTypedListsFragmentsAdapter(
        habits: ArrayList<Habit>,
        private val context: Context,
        childFragmentManager: FragmentManager
) : FragmentPagerAdapter(childFragmentManager) {

    private var goodHabits:ArrayList<Habit> = ArrayList()
    private var badHabits:ArrayList<Habit> =  ArrayList()
    private var habits: HashMap<UUID, Habit> = HashMap()
    private var badHabitsFragment:ListHabitFragment
    private var goodHabitsFragment:ListHabitFragment

    init {
        for (habit in habits){
            when(habit.type){
                HabitType.GOOD -> goodHabits.add(habit)
                HabitType.BAD -> badHabits.add(habit)
            }
            this.habits[habit.id] = habit
        }

        badHabitsFragment= ListHabitFragment.newInstance(badHabits)
        goodHabitsFragment= ListHabitFragment.newInstance(goodHabits)
    }

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment = when(position){
        0 -> goodHabitsFragment
        else -> badHabitsFragment
    }

    override fun getPageTitle(position: Int): CharSequence? = when(position){
        0 -> context.getString(R.string.good_habits)
        else -> context.getString(R.string.bad_habits)
    }

    fun addItem(habit: Habit) {
        when(habit.type){
            HabitType.GOOD ->{
                goodHabitsFragment.addHabit(habit)
            }
            HabitType.BAD ->{
                badHabitsFragment.addHabit(habit)
            }
        }
        habits[habit.id] = habit
    }

    fun updateItem(habit: Habit) {
        val typeWasChanged = habits[habit.id]!!.type != habit.type

        when(habit.type){
            HabitType.GOOD -> {
                if (typeWasChanged) {
                    goodHabitsFragment.addHabit(habit)
                    badHabitsFragment.removeHabit(habit)
                } else {
                    goodHabitsFragment.updateHabit(habit)
                }
            }
            HabitType.BAD ->{
                if (typeWasChanged) {
                    badHabitsFragment.addHabit(habit)
                    goodHabitsFragment.removeHabit(habit)
                } else {
                    badHabitsFragment.updateHabit(habit)
                }
            }
        }
        habits[habit.id] = habit
    }

    fun habitAddOrUpdate(habit: Habit) {
        if(habits.containsKey(habit.id)){
            updateItem(habit)
        }
        else{
            addItem(habit)
        }
        habits[habit.id] = habit
    }
}
