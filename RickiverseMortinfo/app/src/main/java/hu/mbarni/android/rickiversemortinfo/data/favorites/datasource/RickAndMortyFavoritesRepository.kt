package hu.mbarni.android.rickiversemortinfo.data.favorites.datasource

import hu.mbarni.android.rickiversemortinfo.data.favorites.entities.FavoriteEntity
import hu.mbarni.android.rickiversemortinfo.domain.favorites.model.Type
import kotlinx.coroutines.flow.Flow

interface RickAndMortyFavoritesRepository {
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    fun getFavoriteByID(id: Int): Flow<FavoriteEntity>?

    fun getFavoritesByType(type: Type): Flow<List<FavoriteEntity>>

    fun getFavoriteByTypeAndApiID(apiID: Int, type: Type): Flow<FavoriteEntity>?

    suspend fun addFavorite(favorite: FavoriteEntity)

    suspend fun removeFavoriteByTypeAndApiID(apiID: Int, type: Type)
}