package hu.mbarni.android.rickiversemortinfo.domain.favorites.usecases

import hu.mbarni.android.rickiversemortinfo.data.favorites.datasource.RickAndMortyFavoritesRepository
import hu.mbarni.android.rickiversemortinfo.domain.favorites.model.Type

class RemoveFavoriteByTypeAndApiIDUseCase(private val repository: RickAndMortyFavoritesRepository) {
    suspend operator fun invoke(apiID: Int, type: Type) =
        repository.removeFavoriteByTypeAndApiID(apiID = apiID, type = type)
}