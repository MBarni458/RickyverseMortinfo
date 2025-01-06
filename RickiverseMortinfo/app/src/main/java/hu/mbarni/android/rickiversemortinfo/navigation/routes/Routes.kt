package hu.mbarni.android.rickiversemortinfo.navigation.routes

import androidx.navigation.NavController
import hu.mbarni.android.rickiversemortinfo.navigation.Screen

class MainPagesNavigator(private val navController: NavController) {
    fun navigateTo(route: Route) {
        navController.navigate(route.toUrl())
    }
}

enum class Route {
    EPISODES {
        override fun toUrl() = Screen.EpisodeListScreen.route
    },
    CHARACTERS {
        override fun toUrl() = Screen.CharacterListScreen.route
    },
    FAVORITES {
        override fun toUrl() = Screen.FavoritesScreen.route
    };

    abstract fun toUrl(): String
}