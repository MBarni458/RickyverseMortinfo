package hu.mbarni.android.rickiversemortinfo.data.favorites.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.mbarni.android.rickiversemortinfo.domain.favorites.model.Type

@Entity(tableName="favorite_table")
data class FavoriteEntity (
    @PrimaryKey(autoGenerate = true) val id:Int,
    val apiID:Int,
    val type: Type,
)