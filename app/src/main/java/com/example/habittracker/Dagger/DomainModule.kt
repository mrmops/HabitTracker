package com.example.habittracker.Dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.domain.Interfaces.Implementation.HabitRepositoryImplementation
import com.example.domain.OuterInterfaces.HabitDataBaseService
import com.example.domain.OuterInterfaces.HabitNetworkRepository
import com.example.domain.Interfaces.HabitRepository
import com.example.domain.Models.Habit
import com.example.habittracker.ui.fragments.viewModels.HabitViewModel
import com.example.habittracker.ui.fragments.viewModels.HabitsListViewModel
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideHabitRepository(
        habitDBService: HabitDataBaseService,
        networkService: HabitNetworkRepository
    ): HabitRepository {
        return HabitRepositoryImplementation(habitDBService, networkService)
    }

    @Provides
    fun provideHabitsListViewModel(
        habitRepo: HabitRepository,
        lifeCycle: ViewModelStoreOwner,
    ): HabitsListViewModel {
        return ViewModelProvider(lifeCycle, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HabitsListViewModel(habitRepo) as T
            }
        }).get(HabitsListViewModel::class.java)
    }

    @Provides
    fun provideHabitViewModel(
        habit: Habit,
        habitRepo: HabitRepository,
        lifeCycle: ViewModelStoreOwner
    ): HabitViewModel {
        return ViewModelProvider(lifeCycle, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HabitViewModel(habit, habitRepo) as T
            }
        }).get(HabitViewModel::class.java)
    }
}