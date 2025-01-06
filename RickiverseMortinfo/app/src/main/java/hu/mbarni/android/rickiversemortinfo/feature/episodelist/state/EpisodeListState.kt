package hu.mbarni.android.rickiversemortinfo.feature.episodelist.state

import hu.mbarni.android.rickiversemortinfo.network.model.epiosde.EpisodeData

sealed class EpisodeListState {
    data object Loading : EpisodeListState()
    data class Error(val error: Throwable) : EpisodeListState()
    data class Success(val episodeListData: List<EpisodeData>) : EpisodeListState()
}