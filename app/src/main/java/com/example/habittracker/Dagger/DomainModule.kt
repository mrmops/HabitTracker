package com.example.habittracker.Dagger

import com.example.domain.Interfaces.Implementation.HabitRepositoryImplementation
import com.example.domain.OuterInterfaces.HabitDataBaseService
import com.example.domain.OuterInterfaces.HabitNetworkRepository
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