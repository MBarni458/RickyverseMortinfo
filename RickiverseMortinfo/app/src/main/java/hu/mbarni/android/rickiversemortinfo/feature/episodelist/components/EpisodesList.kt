package hu.mbarni.android.rickiversemortinfo.feature.episodelist.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.mbarni.android.rickiversemortinfo.R
import hu.mbarni.android.rickiversemortinfo.network.model.epiosde.EpisodeData
import hu.mbarni.android.rickiversemortinfo.network.model.epiosde.getEpisodePerSeason
import hu.mbarni.android.rickiversemortinfo.network.model.epiosde.getSeason

@Composable
fun EpisodesList(
    episodes: List<EpisodeData>,
    loadMore: () -> Unit,
    openEpisode: (id: Int) -> Unit,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex == episodes.lastIndex) {
                    loadMore()
                }
            }
    }
    LazyColumn(
        state = listState,
        modifier = Modifier.padding(top = 15.dp, bottom = 50.dp, start = 20.dp, end = 20.dp)
    ) {
        items(episodes, key = { it.id }) { episode ->
            if (episode.getEpisodePerSeason() == 1) {
                Text(
                    text = stringResource(id = R.string.season, episode.getSeason()),
                    fontSize = 40.sp
                )
            }
            val contentColor =
                if (episode.id % 2 == 0) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary
            val containerColor =
                if (episode.id % 2 != 0) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary
            EpisodeButton(
                contentColor = contentColor,
                containerColor = containerColor,
                onClick = { openEpisode(episode.id) },
                title = episode.name
            )
        }
    }
}

