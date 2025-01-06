package hu.mbarni.android.rickiversemortinfo.network.model.epiosde

data class EpisodeListInfoData(
    val count: Int,
    val next: String?,
    val pages: Int,
    val prev: String?
)