package com.example.habittracker.Adapters

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.domain.Models.Habit
import com.example.domain.Models.HabitType
import com.example.habittracker.R
import com.example.habittracker.ui.fragments.ListHabitFragment
import kotlin.collections.ArrayList

class HabitTypedListsFragmentsAdapter(
    habits: List<Habit>?,
    private val context: Context,
    private val viewGroup: ViewGroup,
    childFragmentManager: FragmentManager
) : FragmentPagerAdapter(childFragmentManager) {

    companion object{
        private const val GOOD_FRAGMENT_POSITION = 0
        private const val BAD_FRAGMENT_POSITION = 1
    }

    private var goodHabits: ArrayList<Habit> = ArrayList()
    private var badHabits: ArrayList<Habit> = ArrayList()

    init {
        if (habits != null)
            filterHabitsByType(habits)
    }

    fun updateHabits(habits: List<Habit>){
        goodHabits = ArrayList()
        badHabits = ArrayList()
        filterHabitsByType(habits)
        updateHabitsInFragments()
    }

    private fun filterHabitsByType(habits: List<Habit>) {
        for (habit in habits) {
            when (habit.type) {
                HabitType.GOOD -> goodHabits.add(habit)
                HabitType.BAD -> badHabits.add(habit)
            }
        }
    }

    private fun updateHabitsInFragments() {
        val badHabitsFragment = getBadInstancedFragment()
        badHabitsFragment.updateHabits(badHabits)
        val goodHabitsFragment = getGoodInstancedFragment()
        goodHabitsFragment.updateHabits(goodHabits)
    }

    private fun getBadInstancedFragment(): ListHabitFragment =
        instantiateItem(viewGroup, BAD_FRAGMENT_POSITION) as ListHabitFragment

    private fun getGoodInstancedFragment(): ListHabitFragment =
        instantiateItem(viewGroup, GOOD_FRAGMENT_POSITION) as ListHabitFragment

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment =
        when (position) {
            GOOD_FRAGMENT_POSITION -> ListHabitFragment.newInstance(goodHabits)
            else -> ListHabitFragment.newInstance(badHabits)
        }

    override fun getPageTitle(position: Int): CharSequence? = when (position) {
        GOOD_FRAGMENT_POSITION -> context.getString(R.string.good_habits)
        else -> context.getString(R.string.bad_habits)
    }
}
