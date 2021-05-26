package com.example.habittracker

import android.app.Application
import com.example.data.Dagger.DataModule
import com.example.habittracker.Dagger.ApplicationComponent
import com.example.habittracker.Dagger.DaggerApplicationComponent
import com.example.habittracker.Dagger.DomainModule

class HabitApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()
        applicationComponent =
            DaggerApplicationComponent
                .builder()
                .dataModule(DataModule(this))
                .domainModule(DomainModule())
                .build()
    }
}