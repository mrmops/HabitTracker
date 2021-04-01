package com.example.habittracker

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.example.habittracker.Infrastructure.HSVColor
import com.example.habittracker.Infrastructure.HSVColorGradientGenerator
import com.example.habittracker.Infrastructure.firstOrNull
import com.example.habittracker.Models.Habit
import com.example.habittracker.Models.HabitType
import com.example.habittracker.Models.Priority
import com.example.habittracker.ui.fragments.ColorPicker
import com.example.habittracker.ui.fragments.EditHabitFragment
import kotlinx.android.synthetic.main.activity_habit_edit.*

class HabitEditActivity : AppCompatActivity(), EditHabitFragment.IResultCallBack {
    companion object{
        private val LOG_KEY = HabitEditActivity::class.simpleName
        private const val HABIT_KEY = "Habit Key!"
        private const val FRAGMENT_TAG = "Habit Key!"

        fun newIntent(context: Context, habit: Habit): Intent{
            return Intent(context, HabitEditActivity::class.java).apply {
                putExtra(HABIT_KEY, habit)
            }
        }

        fun fromResult(data:Intent): Habit{
            return data.getSerializableExtra(HABIT_KEY) as Habit
        }
    }

    private lateinit var habit: Habit

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_KEY, "OnCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_edit)
        Log.d(LOG_KEY, "setContentView ended")

        val extras = intent.extras
            ?: throw IllegalArgumentException("Активити была запущена не через интент метода newIntent!")
        habit = extras.getSerializable(HABIT_KEY) as Habit

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, EditHabitFragment.newInstance(habit), FRAGMENT_TAG)
            .commit()
        Log.d(LOG_KEY, "OnCreate ended")
    }

    override fun setResult(habit: Habit) {
        setResult(RESULT_OK, Intent().apply { putExtra(HABIT_KEY, habit) })
        finish()
    }
}

