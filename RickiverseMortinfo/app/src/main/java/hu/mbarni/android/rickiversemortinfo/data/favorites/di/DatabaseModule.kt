package hu.mbarni.android.rickiversemortinfo.data.favorites.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.mbarni.android.rickiversemortinfo.data.favorites.RickAndMortyDatabase
import hu.mbarni.android.rickiversemortinfo.data.favorites.dao.RickAndMortyDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabaseInstance(
        @ApplicationContext context: Context
    ): RickAndMortyDatabase = Room.databaseBuilder(
        context,
        RickAndMortyDatabase::class.java,
        "rick_and_morty_database"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideRickAndMortyDao(
        db: RickAndMortyDatabase
    ): RickAndMortyDao = db.dao
}