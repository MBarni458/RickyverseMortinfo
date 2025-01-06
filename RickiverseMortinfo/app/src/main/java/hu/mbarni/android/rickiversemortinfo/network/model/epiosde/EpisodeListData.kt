package hu.mbarni.android.rickiversemortinfo.network.model.epiosde

data class EpisodeListData(
    val info: EpisodeListInfoData,
    val results: List<EpisodeData>
)