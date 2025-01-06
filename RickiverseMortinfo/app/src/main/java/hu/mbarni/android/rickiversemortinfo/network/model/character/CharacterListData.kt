package hu.mbarni.android.rickiversemortinfo.network.model.character

data class CharacterListData(
    val info: CharacterListInfoData,
    val results: List<CharacterData>
)