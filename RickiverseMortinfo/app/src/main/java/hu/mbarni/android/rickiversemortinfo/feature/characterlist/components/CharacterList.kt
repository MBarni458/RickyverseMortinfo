package hu.mbarni.android.rickiversemortinfo.feature.characterlist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.mbarni.android.rickiversemortinfo.feature.componenets.CharacterCard
import hu.mbarni.android.rickiversemortinfo.network.model.character.CharacterData

@Composable
fun CharacterList(
    characters: List<CharacterData>,
    loadMore: () -> Unit,
    openCharacter: (id: Int) -> Unit,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex == characters.lastIndex) {
                    loadMore()
                    listState.scrollToItem(lastVisibleIndex)
                }
            }
    }
    LazyColumn(
        state = listState,
        modifier = Modifier.padding(top = 15.dp, bottom = 50.dp, start = 20.dp, end = 20.dp)
    ) {
        items(characters) { character ->
            Column {
                CharacterCard(
                    onClick = { openCharacter(character.id) },
                    url = character.image,
                    title = character.name,
                    contentColor = MaterialTheme.colorScheme.primary,
                    containerColor = MaterialTheme.colorScheme.surface
                )
                Spacer(modifier = Modifier.padding(5.dp))
            }

        }
    }

}

