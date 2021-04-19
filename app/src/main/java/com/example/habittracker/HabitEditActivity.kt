package com.example.habittracker

import android.R.attr.fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.Models.Habit
import com.example.habittracker.ui.fragments.EditHabitFragment
import com.example.habittracker.ui.fragments.viewModels.HabitViewModel
import kotlinx.android.synthetic.main.activity_habit_edit.*


class HabitEditActivity : AppCompatActivity() {
    companion object {
        private val LOG_KEY = HabitEditActivity::class.simpleName
        private const val HABIT_KEY = "Habit Key!"
        private const val FRAGMENT_TAG = "Habit Key!"

        fun newIntent(context: Context, habit: Habit): Intent {
            return Intent(context, HabitEditActivity::class.java).apply {
                putExtra(HABIT_KEY, habit)
            }
        }

        fun fromResult(data: Intent): Habit {
            return data.getSerializableExtra(HABIT_KEY) as Habit
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_KEY, "OnCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_edit)
        Log.d(LOG_KEY, "setContentView ended")

        val extras = intent.extras
            ?: throw IllegalArgumentException("Активити была запущена не через интент метода newIntent!")
        val habit = extras.getSerializable(HABIT_KEY) as Habit

        val viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HabitViewModel(habit) as T
            }
        }).get(HabitViewModel::class.java)

        val fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)
        if (fragment == null)
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, EditHabitFragment.newInstance(), FRAGMENT_TAG)
                .commit()
        Log.d(LOG_KEY, "OnCreate ended")
    }

    fun onHabitEdited(habit: Habit) {
        setResult(RESULT_OK, Intent().apply { putExtra(HABIT_KEY, habit) })
        finish()
    }
}

