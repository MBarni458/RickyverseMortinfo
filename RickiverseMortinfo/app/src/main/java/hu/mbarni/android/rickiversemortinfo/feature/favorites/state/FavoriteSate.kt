package hu.mbarni.android.rickiversemortinfo.feature.favorites.state

import hu.mbarni.android.rickiversemortinfo.data.favorites.entities.FavoriteEntity

sealed class FavoriteSate {
    data object Loading : FavoriteSate()
    data class Error(val error: Throwable) : FavoriteSate()
    data class Success(val favoriteData: List<FavoriteEntity>) : FavoriteSate()
}