package com.example.habittracker

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.habittracker.Models.Habit
import com.example.habittracker.ui.fragments.EditHabitFragment
import com.example.habittracker.ui.fragments.HabitsListsNavigationFragment
import com.example.habittracker.ui.fragments.HabitsListsNavigationFragmentDirections
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


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
        val header: View = navView.getHeaderView(0)
        val imageView = header.imageView
        Glide.with(this)
            .load("https://source.unsplash.com/random")
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .override(200, 200)
            .placeholder(R.drawable.outline_terrain_white_36)
            .error(R.drawable.outline_report_gmailerrorred_white_36)
            .centerCrop()
            .circleCrop()
            .into(imageView)
    }

    override fun onHabitClick(habit: Habit) {
        Log.d(LOG_KEY, "Move to edit habit with id = ${habit.serverId}")
        directEditHabitFragment(habit)
    }

    override fun onNewHabitButtonClick() {
        val habit = Habit()
        Log.d(LOG_KEY, "Move to create habit with id = ${habit.serverId}")
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