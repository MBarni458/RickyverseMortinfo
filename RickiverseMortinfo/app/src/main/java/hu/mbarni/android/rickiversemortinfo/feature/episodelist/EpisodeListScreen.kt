package hu.mbarni.android.rickiversemortinfo.feature.episodelist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
import hu.mbarni.android.rickiversemortinfo.feature.componenets.BottomBar
import hu.mbarni.android.rickiversemortinfo.feature.episodelist.components.EpisodesList
import hu.mbarni.android.rickiversemortinfo.feature.episodelist.state.EpisodeListState
import hu.mbarni.android.rickiversemortinfo.navigation.routes.MainPagesNavigator
import hu.mbarni.android.rickiversemortinfo.navigation.routes.Route
import hu.mbarni.android.rickiversemortinfo.network.model.epiosde.EpisodeData

@Composable
fun EpisodeListScreen(
    openEpisode: (id: Int) -> Unit,
    navigator: MainPagesNavigator,
    viewModel: EpisodeListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    val currentEpisodes = remember { mutableStateListOf<EpisodeData>() }

    LaunchedEffect(state) {
        if (state is EpisodeListState.Success) {
            state.episodeListData.filter { episode -> !currentEpisodes.contains(episode) }
                .let { newEpisodes ->
                    currentEpisodes.addAll(newEpisodes)
                }

        }
    }
    Scaffold(
        bottomBar = {
            BottomBar(
                modifier = Modifier.padding(top = 200.dp),
                navigator = navigator,
                selected = Route.EPISODES
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 0.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val topPadding = innerPadding.calculateTopPadding()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black)
                    .padding(top = topPadding * 1.5f)
            ) {
                Text(
                    text = stringResource(id = R.string.episodes),
                    fontFamily = Font(R.font.get_schwifty).toFontFamily(),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 50.sp,
                )
            }
            EpisodesList(
                episodes = currentEpisodes,
                loadMore = { viewModel.loadMoreEpisode() },
                openEpisode = openEpisode
            )
        }
    }
}