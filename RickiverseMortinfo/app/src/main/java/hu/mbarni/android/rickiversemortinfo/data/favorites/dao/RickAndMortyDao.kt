package hu.mbarni.android.rickiversemortinfo.data.favorites.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.mbarni.android.rickiversemortinfo.data.favorites.entities.FavoriteEntity
import hu.mbarni.android.rickiversemortinfo.domain.favorites.model.Type
import kotlinx.coroutines.flow.Flow

@Dao
interface RickAndMortyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorite_table")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite_table WHERE type= :type")
    fun getFavoritesByType(type: Type): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite_table WHERE apiID = :apiID AND type = :type")
    fun getFavoriteByTypeAndApiID(type: Type, apiID: Int): Flow<FavoriteEntity>

    @Query("SELECT * FROM favorite_table WHERE id = :id")
    fun getFavoriteByID(id: Int): Flow<FavoriteEntity>

    @Query("DELETE FROM favorite_table WHERE apiID = :apiID AND type = :type")
    suspend fun removeFavoriteByTypeAndApiID(apiID: Int, type: Type)

}