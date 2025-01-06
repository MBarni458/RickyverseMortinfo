package hu.mbarni.android.rickiversemortinfo.feature.favorites

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.mbarni.android.rickiversemortinfo.R
import hu.mbarni.android.rickiversemortinfo.feature.character.state.EpisodesState
import hu.mbarni.android.rickiversemortinfo.feature.componenets.BottomBar
import hu.mbarni.android.rickiversemortinfo.feature.episode.state.CharactersState
import hu.mbarni.android.rickiversemortinfo.feature.favorites.componenets.AnimatedButton
import hu.mbarni.android.rickiversemortinfo.feature.favorites.componenets.FavoritesList
import hu.mbarni.android.rickiversemortinfo.navigation.routes.MainPagesNavigator
import hu.mbarni.android.rickiversemortinfo.navigation.routes.Route

@Composable
fun FavoritesScreen(
    openCharacter: (id: Int) -> Unit,
    openEpisode: (id: Int) -> Unit,
    navigator: MainPagesNavigator,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val episodesState = viewModel.episodeState.collectAsStateWithLifecycle().value
    val charactersState = viewModel.characterState.collectAsStateWithLifecycle().value

    var episodesOnly by remember { mutableStateOf(false) }
    var charactersOnly by remember { mutableStateOf(false) }

    val activeColor = MaterialTheme.colorScheme.primary
    val inactiveColor = MaterialTheme.colorScheme.secondaryContainer

    val episodeColor = remember { Animatable(inactiveColor) }
    val characterColor = remember { Animatable(inactiveColor) }

    LaunchedEffect(episodesOnly) {
        episodeColor.animateTo(
            if (episodesOnly) activeColor else inactiveColor,
            animationSpec = tween(1000)
        )
    }
    LaunchedEffect(charactersOnly) {
        characterColor.animateTo(
            if (charactersOnly) activeColor else inactiveColor,
            animationSpec = tween(1000)
        )
    }
    Scaffold(
        bottomBar = {
            BottomBar(
                modifier = Modifier.padding(top = 200.dp),
                navigator = navigator,
                selected = Route.FAVORITES
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val topPadding = innerPadding.calculateTopPadding()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black)
                    .padding(top = topPadding * 1.5f)
            ) {
                Text(
                    text = stringResource(id = R.string.favorites),
                    fontFamily = Font(R.font.get_schwifty).toFontFamily(),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 50.sp,
                )
            }
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                AnimatedButton(
                    color = episodeColor.value,
                    fontSize = if (!episodesOnly) 20 else 22,
                    title = stringResource(id = R.string.episodes),
                    onClick = {
                        episodesOnly = !episodesOnly
                        charactersOnly = false
                    },
                )
                AnimatedButton(
                    color = characterColor.value,
                    fontSize = if (!charactersOnly) 20 else 22,
                    title = stringResource(id = R.string.characters),
                    onClick = {
                        charactersOnly = !charactersOnly
                        episodesOnly = false
                    })
            }
            when (episodesState) {
                EpisodesState.Loading -> {
                    CircularProgressIndicator()
                }

                is EpisodesState.Success -> {
                    when (charactersState) {
                        CharactersState.Loading -> {
                            CircularProgressIndicator()
                        }

                        is CharactersState.Success -> {
                            FavoritesList(
                                charactersState = charactersState,
                                episodesState = episodesState,
                                episodesOnly = episodesOnly,
                                charactersOnly = charactersOnly,
                                openEpisode = openEpisode,
                                openCharacter = openCharacter
                            )

                        }

                        is CharactersState.Error -> {
                            Text(charactersState.error.toString())
                        }
                    }
                }

                is EpisodesState.Error -> {
                    Text(text = episodesState.error.toString())
                }
            }
        }
    }
}
