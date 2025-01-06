package hu.mbarni.android.rickiversemortinfo.domain.favorites.usecases

import hu.mbarni.android.rickiversemortinfo.data.favorites.datasource.RickAndMortyFavoritesRepository
import hu.mbarni.android.rickiversemortinfo.data.favorites.entities.FavoriteEntity

class AddFavoriteUseCase(private val repository: RickAndMortyFavoritesRepository) {
    suspend operator fun invoke(favorite: FavoriteEntity) = repository.addFavorite(favorite)
}