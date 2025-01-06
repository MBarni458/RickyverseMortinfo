package hu.mbarni.android.rickiversemortinfo.feature.episode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gowtham.ratingbar.RatingBar
import hu.mbarni.android.rickiversemortinfo.R
import hu.mbarni.android.rickiversemortinfo.feature.componenets.CenteredTitle
import hu.mbarni.android.rickiversemortinfo.feature.componenets.IconButton
import hu.mbarni.android.rickiversemortinfo.feature.episode.components.ActionButton
import hu.mbarni.android.rickiversemortinfo.feature.episode.components.CharacterList
import hu.mbarni.android.rickiversemortinfo.feature.episode.components.RateDialog
import hu.mbarni.android.rickiversemortinfo.feature.episode.state.EpisodeState

@Composable
fun EpisodeScreen(
    onNavigateBack: () -> Unit,
    openCharacter: (id: Int) -> Unit,
    viewModel: EpisodeViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val charactersState = viewModel.characterState.collectAsStateWithLifecycle().value
    val isFavorite = viewModel.favorite.collectAsStateWithLifecycle().value
    val totalRating = viewModel.rating.collectAsStateWithLifecycle().value
    val isOpenRateDialog = remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(state = rememberScrollState(), enabled = true),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is EpisodeState.Loading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                is EpisodeState.Success -> {
                    Surface(color = MaterialTheme.colorScheme.background) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                IconButton(
                                    icon = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                                    onClick = onNavigateBack,
                                    iconColor = MaterialTheme.colorScheme.inverseSurface
                                )
                                CenteredTitle(
                                    title = state.episodeData?.name
                                        ?: R.string.unknown.toString(),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                IconButton(
                                    icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    onClick = { viewModel.toggleFavorite() },
                                    iconColor = MaterialTheme.colorScheme.inverseSurface
                                )
                            }
                            Text(
                                text = state.episodeData?.episode
                                    ?: R.string.unknown.toString(),
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.padding(5.dp, 5.dp))

                            val createdAt = stringResource(
                                id = R.string.created_at,
                                state.episodeData?.air_date
                                    ?: stringResource(id = R.string.unknown)
                            )
                            Text(
                                text = createdAt,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.padding(5.dp, 5.dp))
                            RatingBar(
                                value = ((totalRating?.score?.toFloat()
                                    ?: 0.0f) / (totalRating?.votes ?: 1)),
                                numStars = 5,
                                activeColor = MaterialTheme.colorScheme.secondary,
                                inactiveColor = MaterialTheme.colorScheme.secondaryContainer,
                                isIndicator = true,
                                onValueChange = {},
                                onRatingChanged = {}
                            )
                            ActionButton(
                                onClick = { isOpenRateDialog.value = true },
                                contentColor = MaterialTheme.colorScheme.secondary,
                                title = stringResource(
                                    id = R.string.rate
                                ),
                                icon = Icons.Default.AddCircle
                            )
                            Spacer(modifier = Modifier.padding(top = 20.dp))
                            Text(
                                text = stringResource(id = R.string.characters),
                                fontSize = 40.sp,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            CharacterList(
                                charactersState = charactersState,
                                openCharacter = openCharacter
                            )
                            if (isOpenRateDialog.value) {
                                RateDialog(
                                    onDismiss = { isOpenRateDialog.value = false },
                                    onSubmit = { score -> viewModel.addRating(score) })
                            }
                        }
                    }

                }

                is EpisodeState.Error -> {
                    Text(
                        text = state.error.toString(),
                        fontSize = 40.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 41.sp,
                    )
                }
            }
        }
    }
}
