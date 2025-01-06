package hu.mbarni.android.rickiversemortinfo.feature.character.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hu.mbarni.android.rickiversemortinfo.network.model.epiosde.EpisodeData

@Composable
fun EpisodeScroll(episodes: List<EpisodeData>, openEpisode: (id: Int) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(5.dp, 5.dp),
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .size(width = 400.dp, height = 500.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(episodes) { episode ->
            EpisodeCard(
                onClick = { openEpisode(episode.id) },
                contentColor = MaterialTheme.colorScheme.secondary,
                containerColor = Color.White,
                title = episode.name
            )
            Spacer(modifier = Modifier.padding(bottom = 10.dp))
        }
    }
}