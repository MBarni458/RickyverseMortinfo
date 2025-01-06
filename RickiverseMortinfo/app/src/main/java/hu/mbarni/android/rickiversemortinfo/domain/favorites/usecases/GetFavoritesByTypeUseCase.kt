package hu.mbarni.android.rickiversemortinfo.domain.favorites.usecases

import hu.mbarni.android.rickiversemortinfo.data.favorites.datasource.RickAndMortyFavoritesRepository
import hu.mbarni.android.rickiversemortinfo.domain.favorites.model.Type

class GetFavoritesByTypeUseCase(private val repository: RickAndMortyFavoritesRepository) {
    suspend operator fun invoke(type: Type) = repository.getFavoritesByType(type)
}