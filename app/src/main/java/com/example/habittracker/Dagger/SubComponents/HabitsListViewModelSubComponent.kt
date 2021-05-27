package com.example.habittracker.Dagger.SubComponents

import androidx.lifecycle.ViewModelStoreOwner
import com.example.habittracker.Dagger.DomainModule
import com.example.habittracker.ui.fragments.HabitsListsNavigationFragment
import dagger.BindsInstance
import dagger.Subcomponent


@Subcomponent(modules = [DomainModule::class])
interface HabitsListViewModelSubComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(lifeCycle: ViewModelStoreOwner): Builder

        fun build(): HabitsListViewModelSubComponent
    }

    fun inject(fragment: HabitsListsNavigationFragment)
}