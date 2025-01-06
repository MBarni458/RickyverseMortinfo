package hu.mbarni.android.rickiversemortinfo.domain.favorites.model

import hu.mbarni.android.rickiversemortinfo.data.favorites.entities.FavoriteEntity
import hu.mbarni.android.rickiversemortinfo.network.model.character.CharacterData
import hu.mbarni.android.rickiversemortinfo.network.model.epiosde.EpisodeData

fun EpisodeData.toFavorite(): FavoriteEntity = FavoriteEntity(
    apiID = id,
    type = Type.EPISODE,
    id = 0,
)

fun CharacterData.toFavorite(): FavoriteEntity = FavoriteEntity(
    apiID = id,
    type = Type.CHARACTER,
    id = 0,
)