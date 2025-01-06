package hu.mbarni.android.rickiversemortinfo.navigation

sealed class Screen(val route: String) {

    data object EpisodeListScreen : Screen(route = "episodelist")
    data object EpisodeScreen : Screen(route = "episode/{id}") {
        fun passID(id: Int) = "episode/$id"
    }

    data object CharacterListScreen : Screen(route = "characterlist")
    data object CharacterScreen : Screen(route = "character/{id}") {
        fun passID(id: Int) = "character/$id"
    }

    data object FavoritesScreen : Screen(route = "favorites")
}