package hu.mbarni.android.rickiversemortinfo.feature.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import hu.mbarni.android.rickiversemortinfo.R
import hu.mbarni.android.rickiversemortinfo.feature.character.components.EpisodeListStateContainer
import hu.mbarni.android.rickiversemortinfo.feature.character.components.InfoText
import hu.mbarni.android.rickiversemortinfo.feature.character.state.CharacterState
import hu.mbarni.android.rickiversemortinfo.feature.componenets.CenteredTitle
import hu.mbarni.android.rickiversemortinfo.feature.componenets.IconButton

@Composable
fun CharacterScreen(
    onNavigateBack: () -> Unit,
    openEpisode: (id: Int) -> Unit,
    viewModel: CharacterViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val episodesState = viewModel.episodesState.collectAsStateWithLifecycle().value

    val isFavorite = viewModel.favorite.collectAsStateWithLifecycle().value

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(state = rememberScrollState(), enabled = true),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is CharacterState.Loading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                is CharacterState.Success -> {
                    val character = state.characterData
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
                                    title = character?.name ?: R.string.unknown.toString(),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                IconButton(
                                    icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    onClick = { viewModel.toggleFavorite() },
                                    iconColor = MaterialTheme.colorScheme.inverseSurface,
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .background(color = MaterialTheme.colorScheme.primary)
                                    .padding(5.dp)
                                    .clip(
                                        RoundedCornerShape(16.dp)
                                    )
                            ) {
                                AsyncImage(
                                    model = character?.image,
                                    contentDescription = character?.name,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .clip(
                                            RoundedCornerShape(16.dp)
                                        )
                                )
                            }


                            val status = stringResource(
                                id = R.string.status,
                                character?.status ?: R.string.unknown.toString()
                            )
                            InfoText(text = status, color = MaterialTheme.colorScheme.primary)

                            val gender = stringResource(
                                id = R.string.gender,
                                character?.gender ?: R.string.unknown.toString()
                            )
                            InfoText(text = gender, color = MaterialTheme.colorScheme.primary)

                            val species = stringResource(
                                id = R.string.species,
                                character?.species ?: R.string.unknown.toString()
                            )
                            InfoText(text = species, color = MaterialTheme.colorScheme.primary)

                            Spacer(modifier = Modifier.padding(top = 20.dp))
                            Text(
                                text = stringResource(id = R.string.episodes),
                                fontSize = 40.sp,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            EpisodeListStateContainer(
                                episodesState = episodesState,
                                openEpisode = openEpisode
                            )
                        }
                    }

                }

                is CharacterState.Error -> {
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