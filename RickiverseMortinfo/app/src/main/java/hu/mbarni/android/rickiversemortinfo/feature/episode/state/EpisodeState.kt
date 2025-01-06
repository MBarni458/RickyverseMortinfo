package hu.mbarni.android.rickiversemortinfo.feature.episode.state

import hu.mbarni.android.rickiversemortinfo.network.model.epiosde.EpisodeData

sealed class EpisodeState {
    data object Loading : EpisodeState()
    data class Error(val error: Throwable) : EpisodeState()
    data class Success(val episodeData: EpisodeData?) : EpisodeState()
}