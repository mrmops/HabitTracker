package com.example.habittracker.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.habittracker.Adapters.HabitTypedListsFragmentsAdapter
import com.example.habittracker.HabitEditActivity
import com.example.habittracker.MainActivity
import com.example.habittracker.Models.Habit
import com.example.habittracker.R
import kotlinx.android.synthetic.main.fragment_habits.*
import java.util.*
import kotlin.collections.ArrayList

class HabitsListsNavigationFragment : Fragment(), ListHabitFragment.IHabitItemClick {

    companion object{
        private const val HABIT_CREATE = 0
        private const val HABIT_EDIT = 1
        private val LOG_KEY = HabitsListsNavigationFragment::class.java.simpleName
    }

    private val habits:ArrayList<Habit> = ArrayList<Habit>()
    private lateinit var habitTypedListsFragmentsAdapter: HabitTypedListsFragmentsAdapter


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_habits, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habitTypedListsFragmentsAdapter = HabitTypedListsFragmentsAdapter(habits, requireContext(), childFragmentManager)
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
        when(resultCode)
        {
            AppCompatActivity.RESULT_OK -> {
                val habit = HabitEditActivity.fromResult(data!!)
                when(requestCode) {
                    HABIT_CREATE -> {
                        habitTypedListsFragmentsAdapter.addItem(habit)
                    }
                    HABIT_EDIT -> {
                        habitTypedListsFragmentsAdapter.updateItem(habit)
                    }
                }
            }
            AppCompatActivity.RESULT_CANCELED -> Log.e(LOG_KEY, "Canceled requestCode = $requestCode")
            else -> Log.e(LOG_KEY, "requestCode = $requestCode, resultCode = $resultCode")
        }
    }
}