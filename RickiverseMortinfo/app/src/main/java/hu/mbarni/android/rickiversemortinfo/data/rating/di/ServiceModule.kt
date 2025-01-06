package hu.mbarni.android.rickiversemortinfo.data.rating.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.mbarni.android.rickiversemortinfo.data.rating.RatingService
import hu.mbarni.android.rickiversemortinfo.data.rating.firebase.FirebaseEpisodeRatingService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RatingModule {

    @Binds
    @Singleton
    abstract fun bindRatingService(
        firebaseEpisodeRatingService: FirebaseEpisodeRatingService
    ): RatingService
}
