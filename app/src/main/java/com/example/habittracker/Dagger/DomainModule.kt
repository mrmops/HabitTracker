package com.example.habittracker.Dagger

import com.example.domain.HabitRepositoryImplementation
import com.example.domain.Interfaces.HabitDataBaseService
import com.example.domain.Interfaces.HabitNetworkRepository
import com.example.domain.Interfaces.HabitRepository
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
}