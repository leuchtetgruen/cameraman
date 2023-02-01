package de.leuchtetgruen.cameraman.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.leuchtetgruen.cameraman.Config
import de.leuchtetgruen.cameraman.api.CousteauApi
import de.leuchtetgruen.cameraman.api.RuntimeTokenStore
import de.leuchtetgruen.cameraman.buildCousteauApi
import de.leuchtetgruen.cameraman.data.repository.FakeShotDescriptionRepository
import de.leuchtetgruen.cameraman.domain.repository.ShotDescriptionRepository
import de.leuchtetgruen.cameraman.presentation.use_cases.EventuallyRefreshApiToken
import de.leuchtetgruen.cameraman.presentation.use_cases.GetShotDescriptions
import de.leuchtetgruen.cameraman.presentation.use_cases.Login
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestAppModules {

    @Provides
    @Singleton
    fun provideShotDescriptionRepository() : ShotDescriptionRepository = FakeShotDescriptionRepository()

    @Provides
    @Singleton
    fun provideApi(runtimeTokenStore: RuntimeTokenStore) : CousteauApi = buildCousteauApi(runtimeTokenStore, Config.BASE_URL)

    @Provides
    @Singleton
    fun provideTokenStore() : RuntimeTokenStore = RuntimeTokenStore(null, null)

    @Provides
    @Singleton
    fun provideGetShotsDescription(shotDescriptionRepository: ShotDescriptionRepository) : GetShotDescriptions = GetShotDescriptions(shotDescriptionRepository)

    @Provides
    @Singleton
    fun provideLoginUseCase(api: CousteauApi, runtimeTokenStore: RuntimeTokenStore) : Login = Login(api, runtimeTokenStore)

    @Provides
    @Singleton
    fun provideRefreshTokenUseCase(api: CousteauApi, runtimeTokenStore: RuntimeTokenStore) : EventuallyRefreshApiToken = EventuallyRefreshApiToken(api, runtimeTokenStore)
}