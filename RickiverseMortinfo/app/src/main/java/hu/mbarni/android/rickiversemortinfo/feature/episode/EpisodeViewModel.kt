package hu.mbarni.android.rickiversemortinfo.feature.episode

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.mbarni.android.rickiversemortinfo.data.rating.model.Rating
import hu.mbarni.android.rickiversemortinfo.domain.favorites.model.Type
import hu.mbarni.android.rickiversemortinfo.domain.favorites.model.toFavorite
import hu.mbarni.android.rickiversemortinfo.domain.favorites.usecases.FavoriteUseCases
import hu.mbarni.android.rickiversemortinfo.domain.rating.usecases.RatingUseCases
import hu.mbarni.android.rickiversemortinfo.feature.episode.state.CharactersState
import hu.mbarni.android.rickiversemortinfo.feature.episode.state.EpisodeState
import hu.mbarni.android.rickiversemortinfo.network.repository.RetrofitRickAndMortyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import javax.inject.Inject

@HiltViewModel
open class EpisodeViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val rickAndMortyRepository: RetrofitRickAndMortyRepository,
    private val favoriteOperations: FavoriteUseCases,
    private val ratingOperations: RatingUseCases
) : ViewModel() {
    private val _state = MutableStateFlow<EpisodeState>(EpisodeState.Loading)
    val state = _state.asStateFlow()

    private val _characterState = MutableStateFlow<CharactersState>(CharactersState.Loading)
    val characterState = _characterState.asStateFlow()

    private val _favorite = MutableStateFlow(false)
    val favorite = _favorite.asStateFlow()

    private val _rating = MutableStateFlow<Rating?>(null)
    val rating = _rating.asStateFlow()

    private val urlBeforeQuery = "https://rickandmortyapi.com/api/character/"

    fun setEpisodeID(id: Int) {
        getEpisode(id)
    }

    fun addRating(score: Int) {
        if (_state.value !is EpisodeState.Success || (_state.value as EpisodeState.Success).episodeData == null) {
            return
        }
        (_state.value as EpisodeState.Success).episodeData?.id?.let { episodeID ->
            Rating(
                episodeID = episodeID,
                score = (rating.value?.score ?: 0) + score,
                votes = (rating.value?.votes ?: 0) + 1
            ).also { rating ->
                viewModelScope.launch {
                    ratingOperations.addRatingUseCases(rating)
                }
            }
        }
    }

    private fun setupInitialRating(episodeID: Int) {
        _rating.value = Rating(episodeID, 0, 1)
    }

    private fun getRating() {
        if (_state.value !is EpisodeState.Success || (_state.value as EpisodeState.Success).episodeData == null) {
            return
        }
        (_state.value as EpisodeState.Success).episodeData?.id?.let { episodeID ->
            setupInitialRating(episodeID)
            viewModelScope.launch {
                ratingOperations.getRatingUseCase(rating = _rating)
            }
        }
    }

    private suspend fun setFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_state.value is EpisodeState.Success) {
                val episodeData = (_state.value as EpisodeState.Success).episodeData
                episodeData?.let {
                    favoriteOperations.getFavoriteByTypeAndApiIDUseCase(
                        type = Type.EPISODE,
                        apiID = episodeData.id
                    )?.collect { episodeData ->
                        if (episodeData != null) {
                            _favorite.tryEmit(true)
                        }
                    }
                }
            }
        }
    }

    private fun getEpisode(id: Int) {
        viewModelScope.launch {
            _state.value = EpisodeState.Loading
            try {
                rickAndMortyRepository.getEpisode(id)?.awaitResponse().also { response ->
                    if (response == null) {
                        _state.value =
                            EpisodeState.Error(Throwable("The requested object does not exists"))
                        return@also
                    }
                    if (response.isSuccessful) {
                        _state.tryEmit(EpisodeState.Success(response.body()))
                        response.body()?.characters?.map {
                            it.substringAfter(urlBeforeQuery).toInt()
                        }?.let {
                            getCharacters(it)
                        }
                        setFavorite()
                        getRating()
                    }
                }

            } catch (e: Exception) {
                _state.value = EpisodeState.Error(e)
            }
        }
    }

    private fun getCharacters(ids: List<Int>) {
        viewModelScope.launch {
            _characterState.value = CharactersState.Loading
            try {
                val results = ids.map { id ->
                    async {
                        rickAndMortyRepository.getCharacter(id)?.awaitResponse()
                    }
                }.mapNotNull { query ->
                    try {
                        val response = query.await()
                        if (response != null && response.isSuccessful) {
                            response.body()
                        } else {
                            null
                        }
                    } catch (e: Exception) {
                        null
                    }
                }

                if (results.isNotEmpty()) {
                    _characterState.tryEmit(CharactersState.Success(results))
                } else {
                    _characterState.value =
                        CharactersState.Error(Throwable("No characters could be fetched"))
                }
            } catch (e: Exception) {
                _characterState.value = CharactersState.Error(e)
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val currentState = state.value
            if (currentState is EpisodeState.Success) {
                val episodeData = currentState.episodeData ?: return@launch
                try {
                    if (favorite.value) {
                        favoriteOperations.removeFavoriteByTypeAndApiIDUseCase(
                            apiID = episodeData.id,
                            type = Type.EPISODE
                        )
                        _favorite.tryEmit(false)
                    } else {
                        episodeData.toFavorite().let {
                            favoriteOperations.addFavoriteUseCase(it)
                            _favorite.tryEmit(true)
                        }
                    }
                } catch (e: Exception) {
                    _favorite.tryEmit(false)
                }
            }
        }
    }

}