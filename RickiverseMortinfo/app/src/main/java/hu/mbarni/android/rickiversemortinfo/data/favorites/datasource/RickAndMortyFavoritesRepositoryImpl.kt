package hu.mbarni.android.rickiversemortinfo.data.favorites.datasource

import hu.mbarni.android.rickiversemortinfo.data.favorites.dao.RickAndMortyDao
import hu.mbarni.android.rickiversemortinfo.data.favorites.entities.FavoriteEntity
import hu.mbarni.android.rickiversemortinfo.domain.favorites.model.Type
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RickAndMortyFavoritesRepositoryImpl @Inject constructor(
    private val dao: RickAndMortyDao
) : RickAndMortyFavoritesRepository {
    override fun getAllFavorites(): Flow<List<FavoriteEntity>> = dao.getAllFavorites()

    override fun getFavoriteByID(id: Int): Flow<FavoriteEntity> = dao.getFavoriteByID(id)

    override fun getFavoritesByType(type: Type): Flow<List<FavoriteEntity>> =
        dao.getFavoritesByType(type)

    override fun getFavoriteByTypeAndApiID(apiID: Int, type: Type): Flow<FavoriteEntity> =
        dao.getFavoriteByTypeAndApiID(apiID = apiID, type = type)

    override suspend fun addFavorite(favorite: FavoriteEntity) =
        dao.addFavorite(favorite = favorite)

    override suspend fun removeFavoriteByTypeAndApiID(apiID: Int, type: Type) =
        dao.removeFavoriteByTypeAndApiID(apiID = apiID, type = type)

}