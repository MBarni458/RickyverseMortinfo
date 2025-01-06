package hu.mbarni.android.rickiversemortinfo.domain.rating.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.mbarni.android.rickiversemortinfo.data.rating.RatingService
import hu.mbarni.android.rickiversemortinfo.domain.rating.usecases.AddRatingUseCase
import hu.mbarni.android.rickiversemortinfo.domain.rating.usecases.GetRatingUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RatingUseCaseModule {

    @Provides
    @Singleton
    fun providesAddRatingUseCase(service: RatingService) = AddRatingUseCase(service)

    @Provides
    @Singleton
    fun providesGetRatingUseCase(service: RatingService) = GetRatingUseCase(service)
}