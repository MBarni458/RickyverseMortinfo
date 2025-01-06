package hu.mbarni.android.rickiversemortinfo.feature.favorites.componenets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.mbarni.android.rickiversemortinfo.feature.character.components.EpisodeCard
import hu.mbarni.android.rickiversemortinfo.feature.character.state.EpisodesState
import hu.mbarni.android.rickiversemortinfo.feature.componenets.CharacterCard
import hu.mbarni.android.rickiversemortinfo.feature.episode.state.CharactersState
import hu.mbarni.android.rickiversemortinfo.network.model.character.CharacterData
import hu.mbarni.android.rickiversemortinfo.network.model.epiosde.EpisodeData

@Composable
fun FavoritesList(
    charactersState: CharactersState,
    episodesState: EpisodesState,
    episodesOnly: Boolean,
    charactersOnly: Boolean,
    openEpisode: (id: Int) -> Unit,
    openCharacter: (id: Int) -> Unit
) {
    val favoritesList =
        listOf(
            (episodesState as EpisodesState.Success).episodesData,
            (charactersState as CharactersState.Success).charactersData
        )
    Spacer(modifier = Modifier.padding(top = 20.dp))
    LazyColumn(modifier = Modifier.padding(bottom = 50.dp)) {
        items(favoritesList) { favorite ->
            favorite?.forEach {
                when (it) {
                    is EpisodeData -> if (!charactersOnly) {
                        Column {
                            EpisodeCard(
                                onClick = { openEpisode(it.id) },
                                contentColor = MaterialTheme.colorScheme.primary,
                                containerColor = MaterialTheme.colorScheme.background,
                                title = it.name,
                            )
                            Spacer(modifier = Modifier.padding(vertical = 5.dp))
                        }
                    }

                    is CharacterData -> if (!episodesOnly) {
                        Column {
                            CharacterCard(
                                onClick = { openCharacter(it.id) },
                                url = it.image,
                                title = it.name,
                                contentColor = MaterialTheme.colorScheme.primary,
                                containerColor = MaterialTheme.colorScheme.background
                            )
                            Spacer(modifier = Modifier.padding(vertical = 5.dp))
                        }
                    }
                }
            }
        }
    }
}