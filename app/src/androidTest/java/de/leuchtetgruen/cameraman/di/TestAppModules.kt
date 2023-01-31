package de.leuchtetgruen.cameraman.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.leuchtetgruen.cameraman.data.repository.FakeShotDescriptionRepository
import de.leuchtetgruen.cameraman.domain.repository.ShotDescriptionRepository
import de.leuchtetgruen.cameraman.presentation.use_cases.GetShotDescriptions
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestAppModules {

    @Provides
    @Singleton
    fun provideShotDescriptionRepository() : ShotDescriptionRepository = FakeShotDescriptionRepository()

    @Provides
    @Singleton
    fun provideGetShotsDescription(shotDescriptionRepository: ShotDescriptionRepository) : GetShotDescriptions = GetShotDescriptions(shotDescriptionRepository)
}