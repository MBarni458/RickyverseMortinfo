package hu.mbarni.android.rickiversemortinfo.feature.characterlist

import CharacterListState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.mbarni.android.rickiversemortinfo.network.repository.RetrofitRickAndMortyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import javax.inject.Inject

@HiltViewModel
open class CharacterListViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val rickAndMortyRepository: RetrofitRickAndMortyRepository
) : ViewModel() {
    private val _state = MutableStateFlow<CharacterListState>(CharacterListState.Loading)
    val state = _state.asStateFlow()

    private val urlBeforeQuery = "https://rickandmortyapi.com/api/character?page="
    private var nextToLoad = ""

    init {
        getCharacterList(nextToLoad)
    }

    private fun getCharacterList(path: String) {
        viewModelScope.launch {
            val currentCharacters =
                if (state.value is CharacterListState.Success) (state.value as CharacterListState.Success).characterListData else listOf()
            _state.value = CharacterListState.Loading
            try {
                rickAndMortyRepository.getCharacterList(path)?.awaitResponse().also { response ->
                    if (response == null || response.body()?.results == null) {
                        _state.value =
                            CharacterListState.Error(Throwable("The requested object does not exists"))
                        return@launch
                    }
                    if (!response.isSuccessful) {
                        _state.value =
                            CharacterListState.Error(Throwable("The requested data is currently unreachable"))
                        return@launch
                    }
                    response.body()?.results?.let { newCharacters ->
                        _state.tryEmit(
                            CharacterListState.Success(currentCharacters + newCharacters)
                        )
                    }
                    response.body()?.info?.next?.substringAfter(urlBeforeQuery)?.let { query ->
                        nextToLoad = query
                        return@launch
                    }
                }
                _state.tryEmit(
                    CharacterListState.Success(currentCharacters)
                )
            } catch (e: Exception) {
                _state.value = CharacterListState.Error(e)
            }
        }
    }

    fun loadMoreCharacter() {
        getCharacterList(nextToLoad)
    }
}