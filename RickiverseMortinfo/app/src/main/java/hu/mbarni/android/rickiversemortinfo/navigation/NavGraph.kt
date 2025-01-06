package hu.mbarni.android.rickiversemortinfo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.mbarni.android.rickiversemortinfo.feature.character.CharacterScreen
import hu.mbarni.android.rickiversemortinfo.feature.character.CharacterViewModel
import hu.mbarni.android.rickiversemortinfo.feature.characterlist.CharacterListScreen
import hu.mbarni.android.rickiversemortinfo.feature.episode.EpisodeScreen
import hu.mbarni.android.rickiversemortinfo.feature.episode.EpisodeViewModel
import hu.mbarni.android.rickiversemortinfo.feature.episodelist.EpisodeListScreen
import hu.mbarni.android.rickiversemortinfo.feature.favorites.FavoritesScreen
import hu.mbarni.android.rickiversemortinfo.navigation.routes.MainPagesNavigator

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.EpisodeListScreen.route
) {

    var previousPage = ""

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(
            Screen.EpisodeListScreen.route,
        ) {
            EpisodeListScreen(
                openEpisode = { id ->
                    navController.navigate(Screen.EpisodeScreen.passID(id))
                },
                navigator = MainPagesNavigator(navController)
            )
            previousPage = Screen.EpisodeListScreen.route
        }

        composable(
            route = Screen.EpisodeScreen.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("id")?.let { id ->
                if (previousPage != Screen.EpisodeScreen.passID(id)) {
                    val viewModel: EpisodeViewModel = hiltViewModel()
                    viewModel.setEpisodeID(id)
                    previousPage = Screen.EpisodeScreen.passID(id)
                }

            }
            EpisodeScreen(
                onNavigateBack = { navController.popBackStack() },
                openCharacter = { id -> navController.navigate(Screen.CharacterScreen.passID(id)) })
        }

        composable(
            Screen.CharacterListScreen.route,
        ) {
            CharacterListScreen(
                openCharacter = { id ->
                    navController.navigate(Screen.CharacterScreen.passID(id))
                },
                navigator = MainPagesNavigator(navController)
            )
            previousPage = Screen.CharacterListScreen.route
        }

        composable(
            route = Screen.CharacterScreen.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("id")?.let { id ->
                if (previousPage != Screen.CharacterScreen.passID(id)) {
                    val viewModel: CharacterViewModel = hiltViewModel()
                    viewModel.setCharacterID(id)
                    previousPage = Screen.CharacterScreen.passID(id)
                }
            }
            CharacterScreen(
                onNavigateBack = { navController.popBackStack() },
                openEpisode = { id -> navController.navigate(Screen.EpisodeScreen.passID(id)) })
        }

        composable(Screen.FavoritesScreen.route) {
            FavoritesScreen(
                openCharacter = { id ->
                    navController.navigate(Screen.CharacterScreen.passID(id))
                },
                navigator = MainPagesNavigator(navController),
                openEpisode = { id ->
                    navController.navigate(Screen.EpisodeScreen.passID(id))
                })
            previousPage = Screen.FavoritesScreen.route
        }
    }
}

