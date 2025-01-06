package hu.mbarni.android.rickiversemortinfo.network.model.character

data class CharacterListInfoData(
    val count: Int,
    val next: String?,
    val pages: Int,
    val prev: String?
)