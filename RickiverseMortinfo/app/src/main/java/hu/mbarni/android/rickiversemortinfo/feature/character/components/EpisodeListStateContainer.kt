package hu.mbarni.android.rickiversemortinfo.feature.character.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import hu.mbarni.android.rickiversemortinfo.feature.character.state.EpisodesState

@Composable
fun EpisodeListStateContainer(episodesState: EpisodesState, openEpisode: (id: Int) -> Unit) {
    when (episodesState) {
        EpisodesState.Loading -> {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
        }

        is EpisodesState.Success -> {
            EpisodeScroll(
                episodes = episodesState.episodesData ?: listOf(),
                openEpisode = openEpisode
            )
        }

        is EpisodesState.Error -> {
            Text(text = episodesState.error.toString())
        }
    }
}