package com.example.habittracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.Adapters.HabitAdapter

class MainActivity : AppCompatActivity() {

    lateinit var habitRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        habitRecyclerView = findViewById(R.id.habit_recycler_view)
        habitRecyclerView.addItemDecoration(DividerItemDecoration(habitRecyclerView.context, DividerItemDecoration.VERTICAL))
        val habits = ArrayList<Habit>().apply {
            add(Habit("Учить котлин", "Учить котлин очень нужно и важно, но jvm тянет вниз", HabitType.TYPE, Priority.HIGH,"Каждый день"))
            add(Habit("Учить котлин", "Учить котлин очень нужно и важно, но jvm тянет вниз", HabitType.TYPE, Priority.HIGH,"Каждый день"))
        }
        habitRecyclerView.adapter = HabitAdapter(habits)
    }
}