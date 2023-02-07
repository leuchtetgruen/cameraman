package de.leuchtetgruen.cameraman.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.leuchtetgruen.cameraman.Config
import de.leuchtetgruen.cameraman.api.CousteauApi
import de.leuchtetgruen.cameraman.api.RuntimeTokenStore
import de.leuchtetgruen.cameraman.buildCousteauApi
import de.leuchtetgruen.cameraman.businessobjects.TokenProvider
import de.leuchtetgruen.cameraman.businessobjects.TokenProviderImpl
import de.leuchtetgruen.cameraman.domain.repository.ShotDescriptionRepositoryImpl
import de.leuchtetgruen.cameraman.domain.repository.interfaces.ShotDescriptionRepository
import de.leuchtetgruen.cameraman.domain.repository.interfaces.SourcesRepository
import de.leuchtetgruen.cameraman.domain.repository.SourcesRepositoryImpl
import de.leuchtetgruen.cameraman.presentation.use_cases.GetShotDescriptions
import de.leuchtetgruen.cameraman.presentation.use_cases.Login
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModules {

    @Provides
    @Singleton
    fun provideShotDescriptionRepository(api : CousteauApi) : ShotDescriptionRepository = ShotDescriptionRepositoryImpl(api)

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
    fun provideLoginUseCase(api: CousteauApi, runtimeTokenStore: RuntimeTokenStore, tokenProvider: TokenProvider) : Login = Login(api, runtimeTokenStore, tokenProvider)

    @Provides
    @Singleton
    fun provideTokenProvider(@ApplicationContext appContext: Context) : TokenProvider = TokenProviderImpl(appContext)

    @Provides
    @Singleton
    fun providesSourcesRepositoru(api: CousteauApi) : SourcesRepository = SourcesRepositoryImpl(api)
}