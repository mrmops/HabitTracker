package com.example.habittracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.habittracker.Adapters.HabitAdapter
import com.example.habittracker.Models.Habit
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), HabitAdapter.OnItemClickListener, View.OnClickListener {

    companion object{
        private const val HABIT_CREATE = 0
        private const val HABIT_EDIT = 1
        private val LOG_KEY = MainActivity::class.java.simpleName
    }

    private lateinit var habitAdapter: HabitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_KEY, "OnCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        habitRecyclerView.addItemDecoration(DividerItemDecoration(habitRecyclerView.context, DividerItemDecoration.VERTICAL))
        habitAdapter = HabitAdapter(ArrayList())
        habitRecyclerView.adapter = habitAdapter
        habitAdapter.onItemClickListener = this

        fab.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(LOG_KEY, "requestCode = $requestCode, resultCode = $resultCode")
        when(resultCode)
        {
            RESULT_OK -> {
                val habit = HabitEditActivity.fromResult(data!!)
                when(requestCode) {
                    HABIT_CREATE -> {
                        habitAdapter.addItem(habit)
                    }
                    HABIT_EDIT -> {
                        habitAdapter.updateItem(habit)
                    }
                }
            }
            RESULT_CANCELED -> Log.e(LOG_KEY, "Canceled requestCode = $requestCode")
            else -> Log.e(LOG_KEY, "requestCode = $requestCode, resultCode = $resultCode")
        }
    }

    override fun onItemClick(habit: Habit) {
        Log.d(LOG_KEY, "Move to edit habit with id = ${habit.id}")
        val intent = HabitEditActivity.newIntent(this, habit)
        startActivityForResult(intent, HABIT_EDIT)
    }

    override fun onClick(v: View?) {
        val habit = Habit().apply { id = UUID.randomUUID() }
        Log.d(LOG_KEY, "Move to create habit with id = ${habit.id}")
        val intent = HabitEditActivity.newIntent(this, habit)
        startActivityForResult(intent, HABIT_CREATE)
    }
}