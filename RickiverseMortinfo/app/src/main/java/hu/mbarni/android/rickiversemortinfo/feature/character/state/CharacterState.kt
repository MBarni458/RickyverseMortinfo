package hu.mbarni.android.rickiversemortinfo.feature.character.state

import hu.mbarni.android.rickiversemortinfo.network.model.character.CharacterData

sealed class CharacterState {
    data object Loading : CharacterState()
    data class Error(val error: Throwable) : CharacterState()
    data class Success(val characterData: CharacterData?) : CharacterState()
}