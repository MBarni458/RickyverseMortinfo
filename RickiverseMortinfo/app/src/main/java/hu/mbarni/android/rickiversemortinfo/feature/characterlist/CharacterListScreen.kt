package hu.mbarni.android.rickiversemortinfo.feature.characterlist

import CharacterListState
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
import hu.mbarni.android.rickiversemortinfo.feature.characterlist.components.CharacterList
import hu.mbarni.android.rickiversemortinfo.feature.componenets.BottomBar
import hu.mbarni.android.rickiversemortinfo.navigation.routes.MainPagesNavigator
import hu.mbarni.android.rickiversemortinfo.navigation.routes.Route
import hu.mbarni.android.rickiversemortinfo.network.model.character.CharacterData

@Composable
fun CharacterListScreen(
    openCharacter: (id: Int) -> Unit,
    navigator: MainPagesNavigator,
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    val currentCharacters = remember { mutableStateListOf<CharacterData>() }

    LaunchedEffect(state) {
        if (state is CharacterListState.Success) {
            state.characterListData
                .filter { character -> !currentCharacters.contains(character) }
                .let { newCharacters ->
                    currentCharacters.addAll(newCharacters)
                }

        }
    }
    Scaffold(
        bottomBar = {
            BottomBar(
                modifier = Modifier.padding(top = 200.dp),
                navigator = navigator,
                selected = Route.CHARACTERS
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
                    text = stringResource(id = R.string.characters),
                    fontFamily = Font(R.font.get_schwifty).toFontFamily(),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 50.sp,
                )
            }
            CharacterList(
                characters = currentCharacters,
                loadMore = { viewModel.loadMoreCharacter() },
                openCharacter = openCharacter
            )
        }
    }
}