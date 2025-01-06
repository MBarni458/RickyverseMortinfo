package hu.mbarni.android.rickiversemortinfo.data.favorites.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.mbarni.android.rickiversemortinfo.data.favorites.datasource.RickAndMortyFavoritesRepository
import hu.mbarni.android.rickiversemortinfo.data.favorites.datasource.RickAndMortyFavoritesRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRickAndMortyFavoritesRepository(
        rickAndMortyFavoritesRepositoryImpl: RickAndMortyFavoritesRepositoryImpl
    ): RickAndMortyFavoritesRepository

}