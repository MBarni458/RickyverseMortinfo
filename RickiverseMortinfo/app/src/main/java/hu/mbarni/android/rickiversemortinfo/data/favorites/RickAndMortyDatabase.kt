package hu.mbarni.android.rickiversemortinfo.data.favorites

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.mbarni.android.rickiversemortinfo.data.favorites.converters.TypeConverter
import hu.mbarni.android.rickiversemortinfo.data.favorites.dao.RickAndMortyDao
import hu.mbarni.android.rickiversemortinfo.data.favorites.entities.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract val dao: RickAndMortyDao
}