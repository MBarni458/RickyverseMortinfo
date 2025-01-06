package hu.mbarni.android.rickiversemortinfo.domain.favorites.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.mbarni.android.rickiversemortinfo.data.favorites.datasource.RickAndMortyFavoritesRepository
import hu.mbarni.android.rickiversemortinfo.domain.favorites.usecases.AddFavoriteUseCase
import hu.mbarni.android.rickiversemortinfo.domain.favorites.usecases.GetAllFavoritesUseCase
import hu.mbarni.android.rickiversemortinfo.domain.favorites.usecases.GetFavoriteByTypeAndApiIDUseCase
import hu.mbarni.android.rickiversemortinfo.domain.favorites.usecases.GetFavoritesByTypeUseCase
import hu.mbarni.android.rickiversemortinfo.domain.favorites.usecases.RemoveFavoriteByTypeAndApiIDUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FavoriteUseCaseModule {

    @Provides
    @Singleton
    fun providesAddFavoriteUseCase(repository: RickAndMortyFavoritesRepository) =
        AddFavoriteUseCase(repository)

    @Provides
    @Singleton
    fun providesGetAllFavoritesUseCase(repository: RickAndMortyFavoritesRepository) =
        GetAllFavoritesUseCase(repository)

    @Provides
    @Singleton
    fun providesGetFavoritesByTypeAndApiIDUseCase(repository: RickAndMortyFavoritesRepository) =
        GetFavoriteByTypeAndApiIDUseCase(repository)

    @Provides
    @Singleton
    fun providesGetFavoritesByTypeUseCase(repository: RickAndMortyFavoritesRepository) =
        GetFavoritesByTypeUseCase(repository)

    @Provides
    @Singleton
    fun providesRemoveFavoritesByTypeAndApiID(repository: RickAndMortyFavoritesRepository) =
        RemoveFavoriteByTypeAndApiIDUseCase(repository)
}