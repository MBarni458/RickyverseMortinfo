package hu.mbarni.android.rickiversemortinfo.network.repository

import hu.mbarni.android.rickiversemortinfo.network.model.character.CharacterData
import hu.mbarni.android.rickiversemortinfo.network.model.character.CharacterListData
import hu.mbarni.android.rickiversemortinfo.network.model.epiosde.EpisodeData
import hu.mbarni.android.rickiversemortinfo.network.model.epiosde.EpisodeListData
import retrofit2.Call

interface IRickAndMortyRepository {
    suspend fun getEpisode(id: Int): Call<EpisodeData>?
    suspend fun getEpisodeList(path: String): Call<EpisodeListData>?
    suspend fun getCharacter(id: Int): Call<CharacterData>?
    suspend fun getCharacterList(path: String): Call<CharacterListData>?
}