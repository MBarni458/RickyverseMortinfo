package hu.mbarni.android.rickiversemortinfo.domain.favorites.usecases

import hu.mbarni.android.rickiversemortinfo.data.favorites.datasource.RickAndMortyFavoritesRepository
import javax.inject.Inject

class FavoriteUseCases @Inject constructor(
    val repository: RickAndMortyFavoritesRepository,
    val addFavoriteUseCase: AddFavoriteUseCase,
    val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    val getFavoriteByTypeAndApiIDUseCase: GetFavoriteByTypeAndApiIDUseCase,
    val getFavoritesByTypeUseCase: GetFavoritesByTypeUseCase,
    val removeFavoriteByTypeAndApiIDUseCase: RemoveFavoriteByTypeAndApiIDUseCase
)