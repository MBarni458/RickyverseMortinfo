package hu.mbarni.android.rickiversemortinfo.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.mbarni.android.rickiversemortinfo.network.repository.IRickAndMortyRepository
import hu.mbarni.android.rickiversemortinfo.network.repository.RetrofitRickAndMortyRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRickAndMortyRepository(
        retrofitRickAndMortyRepository: RetrofitRickAndMortyRepository
    ): IRickAndMortyRepository

}