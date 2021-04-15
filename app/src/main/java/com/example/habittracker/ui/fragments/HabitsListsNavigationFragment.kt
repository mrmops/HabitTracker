package com.example.habittracker.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.Adapters.HabitTypedListsFragmentsAdapter
import com.example.habittracker.HabitEditActivity
import com.example.habittracker.Models.Habit
import com.example.habittracker.R
import com.example.habittracker.ui.fragments.viewModels.SortedAndFilteredHabitsListViewModel
import kotlinx.android.synthetic.main.fragment_habits.*
import java.util.*


class HabitsListsNavigationFragment : Fragment(), ListHabitFragment.IHabitItemClick {

    companion object {
        private const val HABIT_CREATE = 0
        private const val HABIT_EDIT = 1
        private val LOG_KEY = HabitsListsNavigationFragment::class.java.name
    }

    private lateinit var viewModelSortedAndFilteredHabits: SortedAndFilteredHabitsListViewModel;
    private lateinit var habitTypedListsFragmentsAdapter: HabitTypedListsFragmentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelSortedAndFilteredHabits = ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SortedAndFilteredHabitsListViewModel() as T
            }
        }).get(SortedAndFilteredHabitsListViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_habits, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomFragment = ListSortsAndOrdersDialogFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.containerBottomSheet, bottomFragment)
            .commit()

        habitTypedListsFragmentsAdapter = HabitTypedListsFragmentsAdapter(
            viewModelSortedAndFilteredHabits,
            this,
            requireContext(),
            habitsViewPager,
            childFragmentManager
        )
        habitsViewPager.adapter = habitTypedListsFragmentsAdapter
        habitsPagerTabLayout.setupWithViewPager(habitsViewPager)

        fab.setOnClickListener {
            val habit = Habit().apply { id = UUID.randomUUID() }
            Log.d(LOG_KEY, "Move to create habit with id = ${habit.id}")
            val intent = HabitEditActivity.newIntent(requireContext(), habit)
            startActivityForResult(intent, HABIT_CREATE)
        }
    }

    override fun onHabitItemClick(habit: Habit) {
        Log.d(LOG_KEY, "Move to edit habit with id = ${habit.id}")
        val intent = HabitEditActivity.newIntent(requireContext(), habit)
        startActivityForResult(intent, HABIT_EDIT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(LOG_KEY, "requestCode = $requestCode, resultCode = $resultCode")
        when (resultCode) {
            AppCompatActivity.RESULT_OK -> {
                val habit = HabitEditActivity.fromResult(data!!)
                when (requestCode) {
                    HABIT_CREATE -> {
                        viewModelSortedAndFilteredHabits.addOrUpdateHabit(habit)
                    }
                    HABIT_EDIT -> {
                        viewModelSortedAndFilteredHabits.addOrUpdateHabit(habit)
                    }
                }
            }
            else -> Log.e(LOG_KEY, "requestCode = $requestCode, resultCode = $resultCode")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_KEY, "On destroy")
    }
}