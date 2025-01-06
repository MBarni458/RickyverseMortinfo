package hu.mbarni.android.rickiversemortinfo.feature.episode.components

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
import hu.mbarni.android.rickiversemortinfo.feature.componenets.CharacterCard
import hu.mbarni.android.rickiversemortinfo.network.model.character.CharacterData

@Composable
fun CharacterScroll(characters: List<CharacterData>, openCharacter: (id: Int) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(5.dp, 5.dp),
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .size(height = 500.dp, width = 400.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(characters) { character ->
            CharacterCard(
                onClick = { openCharacter(character.id) },
                url = character.image,
                title = character.name,
                contentColor = MaterialTheme.colorScheme.secondary,
                containerColor = Color.White
            )
            Spacer(modifier = Modifier.padding(bottom = 10.dp))
        }
    }
}