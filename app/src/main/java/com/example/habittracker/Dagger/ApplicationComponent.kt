package com.example.habittracker.Dagger

import com.example.data.Dagger.DataModule
import com.example.domain.Interfaces.HabitRepository
import com.example.habittracker.Dagger.SubComponents.HabitViewModelSubComponent
import com.example.habittracker.Dagger.SubComponents.HabitsListViewModelSubComponent
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [DomainModule::class, DataModule::class])
interface ApplicationComponent {

    fun getHabitRepository(): HabitRepository

    fun habitsListViewModelSubComponentBuilder(): HabitsListViewModelSubComponent.Builder
    fun habitViewModelSubComponentBuilder(): HabitViewModelSubComponent.Builder


}