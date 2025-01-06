package hu.mbarni.android.rickiversemortinfo.feature.episode.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import hu.mbarni.android.rickiversemortinfo.feature.episode.state.CharactersState

@Composable
fun CharacterList(charactersState: CharactersState, openCharacter: (id: Int) -> Unit) {
    when (charactersState) {
        is CharactersState.Loading -> {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
        }

        is CharactersState.Success -> {
            CharacterScroll(
                charactersState.charactersData ?: listOf(),
                openCharacter = openCharacter
            )
        }

        is CharactersState.Error -> {
            Text(text = charactersState.error.toString())
        }

    }
}