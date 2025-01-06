package hu.mbarni.android.rickiversemortinfo.feature.character.state

import hu.mbarni.android.rickiversemortinfo.network.model.epiosde.EpisodeData

sealed class EpisodesState {
    data object Loading : EpisodesState()
    data class Error(val error: Throwable) : EpisodesState()
    data class Success(val episodesData: List<EpisodeData>?) : EpisodesState()
}