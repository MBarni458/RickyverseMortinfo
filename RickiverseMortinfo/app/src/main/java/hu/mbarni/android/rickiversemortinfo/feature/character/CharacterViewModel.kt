package hu.mbarni.android.rickiversemortinfo.feature.character

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.mbarni.android.rickiversemortinfo.domain.favorites.model.Type
import hu.mbarni.android.rickiversemortinfo.domain.favorites.model.toFavorite
import hu.mbarni.android.rickiversemortinfo.domain.favorites.usecases.FavoriteUseCases
import hu.mbarni.android.rickiversemortinfo.feature.character.state.CharacterState
import hu.mbarni.android.rickiversemortinfo.feature.character.state.EpisodesState
import hu.mbarni.android.rickiversemortinfo.network.repository.RetrofitRickAndMortyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import javax.inject.Inject

@HiltViewModel
open class CharacterViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val rickAndMortyRepository: RetrofitRickAndMortyRepository,
    private val favoriteOperations: FavoriteUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow<CharacterState>(CharacterState.Loading)
    val state = _state.asStateFlow()

    private val _episodesState = MutableStateFlow<EpisodesState>(EpisodesState.Loading)
    val episodesState = _episodesState.asStateFlow()

    private val _favorite = MutableStateFlow(false)
    val favorite = _favorite.asStateFlow()

    private val urlBeforeQuery = "https://rickandmortyapi.com/api/episode/"

    fun setCharacterID(id: Int) {
        getCharacter(id)
    }

    private suspend fun getFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_state.value is CharacterState.Success) {
                val characterData = (_state.value as CharacterState.Success).characterData
                characterData?.let {
                    favoriteOperations.getFavoriteByTypeAndApiIDUseCase(
                        type = Type.CHARACTER,
                        apiID = characterData.id
                    )?.collect { character ->
                        if (character != null) {
                            _favorite.tryEmit(true)
                        }
                    }
                }
            }
        }
    }

    private fun getCharacter(id: Int) {
        viewModelScope.launch {
            _state.value = CharacterState.Loading
            try {
                rickAndMortyRepository.getCharacter(id)?.awaitResponse().also { response ->
                    if (response == null) {
                        _state.value =
                            CharacterState.Error(Throwable("The requested object does not exists"))
                        return@also
                    }
                    if (response.isSuccessful) {
                        _state.tryEmit(CharacterState.Success(response.body()))
                        response.body()?.episode?.map {
                            it.substringAfter(urlBeforeQuery).toInt()
                        }?.let { ids ->
                            getEpisodes(ids)
                        }
                        getFavorite()
                    }
                }

            } catch (e: Exception) {
                _state.value = CharacterState.Error(e)
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
                if (results.isNotEmpty()) {
                    _episodesState.tryEmit(EpisodesState.Success(results))
                } else {
                    _episodesState.value =
                        EpisodesState.Error(Throwable("No characters could be fetched"))
                }
            } catch (e: Exception) {
                _episodesState.value = EpisodesState.Error(e)
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val currentState = state.value
            if (currentState is CharacterState.Success) {
                val characterData = currentState.characterData ?: return@launch
                try {
                    if (favorite.value) {
                        favoriteOperations.removeFavoriteByTypeAndApiIDUseCase(
                            apiID = characterData.id,
                            type = Type.CHARACTER
                        )
                        _favorite.tryEmit(false)
                    } else {
                        characterData.toFavorite().let {
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