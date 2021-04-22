package com.example.habittracker

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.habittracker.DataBase.HabitsDataBase
import com.example.habittracker.DataBase.HabitsDataBase_Impl
import com.example.habittracker.Models.Habit
import com.example.habittracker.ui.fragments.EditHabitFragment
import com.example.habittracker.ui.fragments.EditHabitFragmentDirections
import com.example.habittracker.ui.fragments.HabitsListsNavigationFragment
import com.example.habittracker.ui.fragments.HabitsListsNavigationFragmentDirections
import com.example.habittracker.ui.fragments.viewModels.SortedAndFilteredHabitsListViewModel
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class NavigationActivity : AppCompatActivity(),
    HabitsListsNavigationFragment.ClickHabitItemCallBack, EditHabitFragment.IResultCallBack {

    companion object {
        private val LOG_KEY = NavigationActivity::class.java.simpleName
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        setSupportActionBar(toolbar)

        navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.nav_habits, R.id.nav_about_app), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onHabitClick(habit: Habit) {
        Log.d(LOG_KEY, "Move to edit habit with id = ${habit.id}")
        directEditHabitFragment(habit)
    }

    override fun onNewHabitButtonClick() {
        val habit = Habit()
        Log.d(LOG_KEY, "Move to create habit with id = ${habit.id}")
        directEditHabitFragment(habit)
    }

    private fun directEditHabitFragment(habit: Habit) {
        val direction =
            HabitsListsNavigationFragmentDirections.actionNavHabitsToEditHabitFragment(habit)
        navController.navigate(direction)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation_actvity, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onFinishEditFragment() { navController.popBackStack() }
}