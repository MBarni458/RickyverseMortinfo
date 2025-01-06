import hu.mbarni.android.rickiversemortinfo.network.model.character.CharacterData

sealed class CharacterListState {
    data object Loading : CharacterListState()
    data class Error(val error: Throwable) : CharacterListState()
    data class Success(val characterListData: List<CharacterData>) : CharacterListState()
}