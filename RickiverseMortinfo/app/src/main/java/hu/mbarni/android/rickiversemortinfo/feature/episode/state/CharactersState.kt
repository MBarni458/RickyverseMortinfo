package hu.mbarni.android.rickiversemortinfo.feature.episode.state

import hu.mbarni.android.rickiversemortinfo.network.model.character.CharacterData

sealed class CharactersState {
    data object Loading : CharactersState()
    data class Error(val error: Throwable) : CharactersState()
    data class Success(val charactersData: List<CharacterData>?) : CharactersState()
}