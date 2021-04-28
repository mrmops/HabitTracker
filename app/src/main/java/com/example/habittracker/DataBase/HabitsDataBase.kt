package com.example.habittracker.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.habittracker.Models.Habit


@Database(entities = [Habit::class], version = 1)
abstract class HabitsDataBase() : RoomDatabase() {

    abstract fun habitDao(): HabitDao

    companion object {
        private const val DB_NAME = "habitDatabase.db"

        private var instance: HabitsDataBase? = null


        private fun create(context: Context): HabitsDataBase {
            return Room.databaseBuilder(
                context,
                HabitsDataBase::class.java,
                DB_NAME
            ).build()
        }

        fun getInstance(context: Context): HabitsDataBase {
            if (instance == null) {
                instance = create(context)
            }
            return instance!!
        }
    }
}