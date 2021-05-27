package com.example.habittracker.Dagger.SubComponents

import androidx.lifecycle.ViewModelStoreOwner
import com.example.domain.Models.Habit
import com.example.habittracker.Dagger.DomainModule
import com.example.habittracker.ui.fragments.ColorPicker
import com.example.habittracker.ui.fragments.EditHabitFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [DomainModule::class])
interface HabitViewModelSubComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(lifeCycle: ViewModelStoreOwner): Builder

        @BindsInstance
        fun withHabit(habit: Habit): Builder

        fun build(): HabitViewModelSubComponent
    }

    fun inject(fragment: EditHabitFragment)

    fun inject(fragment: ColorPicker)
}