package hu.mbarni.android.rickiversemortinfo.data.favorites.converters

import androidx.room.TypeConverter
import hu.mbarni.android.rickiversemortinfo.domain.favorites.model.Type

object TypeConverter {

    @TypeConverter
    fun Type.asString(): String = this.name

    @TypeConverter
    fun String.asType(): Type {
        return when (this) {
            Type.EPISODE.name -> Type.EPISODE
            Type.CHARACTER.name -> Type.CHARACTER
            else -> Type.NONE
        }
    }

}