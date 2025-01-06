package hu.mbarni.android.rickiversemortinfo.feature.episodelist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.mbarni.android.rickiversemortinfo.feature.episodelist.state.EpisodeListState
import hu.mbarni.android.rickiversemortinfo.network.repository.RetrofitRickAndMortyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import javax.inject.Inject

@HiltViewModel
open class EpisodeListViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val rickAndMortyRepository: RetrofitRickAndMortyRepository
) : ViewModel() {
    private val _state = MutableStateFlow<EpisodeListState>(EpisodeListState.Loading)
    val state = _state.asStateFlow()

    private val urlBeforeQuery = "https://rickandmortyapi.com/api/episode?page="
    private var nextToLoad = ""

    init {
        getEpisodeList(nextToLoad)
    }

    private fun getEpisodeList(path: String) {
        viewModelScope.launch {
            val currentEpisodes =
                if (state.value is EpisodeListState.Success) (state.value as EpisodeListState.Success).episodeListData else listOf()
            _state.value = EpisodeListState.Loading
            try {
                rickAndMortyRepository.getEpisodeList(path)?.awaitResponse().also { response ->
                    if (response == null || response.body()?.results == null) {
                        _state.value =
                            EpisodeListState.Error(Throwable("The requested object does not exists"))
                        return@launch
                    }
                    if (!response.isSuccessful) {
                        _state.value =
                            EpisodeListState.Error(Throwable("The requested data is currently unreachable"))
                        return@launch
                    }
                    response.body()?.results?.let { newEpisodes ->
                        _state.tryEmit(
                            EpisodeListState.Success(currentEpisodes + newEpisodes)
                        )
                    }
                    response.body()?.info?.next?.substringAfter(urlBeforeQuery)?.let { query ->
                        nextToLoad = query
                        return@launch
                    }
                    nextToLoad = "-1"
                }
            } catch (e: Exception) {
                _state.value = EpisodeListState.Error(e)
            }
        }
    }

    fun loadMoreEpisode() {
        if (nextToLoad != "-1") {
            getEpisodeList(nextToLoad)
        }
    }
}