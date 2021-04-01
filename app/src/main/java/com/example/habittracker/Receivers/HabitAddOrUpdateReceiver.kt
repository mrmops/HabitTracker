package com.example.habittracker.Receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.habittracker.Infrastructure.BroadcastActions
import com.example.habittracker.Models.Habit

class HabitAddOrUpdateReceiver(
    private val habitListener:IHabitListener
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == BroadcastActions.HABIT_CREATE_OR_UPDATE){
            val habit = intent.getSerializableExtra(BroadcastActions.HABIT_KEY)
            habitListener.habitAddOrUpdate(habit as Habit)
        }
    }

    public interface IHabitListener{
        fun habitAddOrUpdate(habit: Habit)
    }
}