package hu.mbarni.android.rickiversemortinfo.feature.favorites

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.mbarni.android.rickiversemortinfo.domain.favorites.model.Type
import hu.mbarni.android.rickiversemortinfo.domain.favorites.usecases.FavoriteUseCases
import hu.mbarni.android.rickiversemortinfo.feature.character.state.EpisodesState
import hu.mbarni.android.rickiversemortinfo.feature.episode.state.CharactersState
import hu.mbarni.android.rickiversemortinfo.feature.favorites.state.FavoriteSate
import hu.mbarni.android.rickiversemortinfo.network.repository.RetrofitRickAndMortyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val rickAndMortyRepository: RetrofitRickAndMortyRepository,
    private val favoriteOperations: FavoriteUseCases
) : ViewModel() {
    private val _state = MutableStateFlow<FavoriteSate>(FavoriteSate.Loading)
    val state = _state.asStateFlow()

    private val _episodesState = MutableStateFlow<EpisodesState>(EpisodesState.Loading)
    val episodeState = _episodesState.asStateFlow()

    private val _charactersState = MutableStateFlow<CharactersState>(CharactersState.Loading)
    val characterState = _charactersState.asStateFlow()

    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch {
            _state.value = FavoriteSate.Loading
            try {
                CoroutineScope(coroutineContext).launch(
                    Dispatchers.IO
                ) {
                    favoriteOperations.getAllFavoritesUseCase()
                        .collect { favorites ->
                            favorites.filter { favorite -> favorite.type == Type.EPISODE }
                                .map { episode -> episode.apiID }.let { episodeIDs ->
                                    getEpisodes(episodeIDs)
                                }
                            favorites.filter { favorite -> favorite.type == Type.CHARACTER }
                                .map { character -> character.apiID }.let { characterIDs ->
                                    getCharacters(characterIDs)
                                }
                        }
                }

            } catch (e: Exception) {
                _state.value = FavoriteSate.Error(e)
            }
        }
    }

    private fun getEpisodes(ids: List<Int>) {
        viewModelScope.launch {
            _episodesState.value = EpisodesState.Loading
            try {
                val results = ids.map { id ->
                    async {
                        rickAndMortyRepository.getEpisode(id)?.awaitResponse()
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
                _episodesState.tryEmit(EpisodesState.Success(results))
            } catch (e: Exception) {
                _episodesState.value = EpisodesState.Error(e)
            }
        }
    }

    private fun getCharacters(ids: List<Int>) {
        viewModelScope.launch {
            _charactersState.value = CharactersState.Loading
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
                _charactersState.tryEmit(CharactersState.Success(results))
            } catch (e: Exception) {
                _charactersState.value = CharactersState.Error(e)
            }
        }
    }

}