package hu.mbarni.android.rickiversemortinfo.domain.favorites.usecases

import hu.mbarni.android.rickiversemortinfo.data.favorites.datasource.RickAndMortyFavoritesRepository

class GetAllFavoritesUseCase(private val repository: RickAndMortyFavoritesRepository) {
    suspend operator fun invoke() = repository.getAllFavorites()
}