package com.example.habittracker.Dagger

import com.example.data.Dagger.DataModule
import com.example.domain.Interfaces.HabitRepository
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [DomainModule::class, DataModule::class])
interface ApplicationComponent {

    fun getHabitRepository(): HabitRepository
}