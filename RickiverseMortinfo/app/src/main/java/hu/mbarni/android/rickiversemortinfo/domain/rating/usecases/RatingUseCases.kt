package hu.mbarni.android.rickiversemortinfo.domain.rating.usecases

import hu.mbarni.android.rickiversemortinfo.data.favorites.datasource.RickAndMortyFavoritesRepository
import javax.inject.Inject

class RatingUseCases @Inject constructor(
    val service: RickAndMortyFavoritesRepository,
    val addRatingUseCases: AddRatingUseCase,
    val getRatingUseCase: GetRatingUseCase
)