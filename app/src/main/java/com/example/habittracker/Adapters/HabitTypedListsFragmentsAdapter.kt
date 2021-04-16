package com.example.habittracker.Adapters

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.LifecycleOwner
import com.example.habittracker.Models.Habit
import com.example.habittracker.Models.HabitType
import com.example.habittracker.R
import com.example.habittracker.ui.fragments.ListHabitFragment
import com.example.habittracker.ui.fragments.viewModels.SortedAndFilteredHabitsListViewModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HabitTypedListsFragmentsAdapter(
    viewModelSortedAndFilteredHabits: SortedAndFilteredHabitsListViewModel,
    lifeCycle: LifecycleOwner,
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
    private var habitsIdMap: HashMap<UUID, Habit> = HashMap()

    init {
        val habits = viewModelSortedAndFilteredHabits.listHabits.value
        if (habits != null)
            filterHabitsByType(habits)

        viewModelSortedAndFilteredHabits.listHabits.observe(lifeCycle, { list ->
            goodHabits = ArrayList()
            badHabits = ArrayList()
            filterHabitsByType(list)
            updateHabitsInFragments()
        })
    }

    private fun filterHabitsByType(habits: List<Habit>) {
        for (habit in habits) {
            when (habit.type) {
                HabitType.GOOD -> goodHabits.add(habit)
                HabitType.BAD -> badHabits.add(habit)
            }
            this.habitsIdMap[habit.id] = habit
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
