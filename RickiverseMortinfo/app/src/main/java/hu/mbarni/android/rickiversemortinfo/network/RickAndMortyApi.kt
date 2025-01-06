package hu.mbarni.android.rickiversemortinfo.network

import hu.mbarni.android.rickiversemortinfo.network.model.character.CharacterData
import hu.mbarni.android.rickiversemortinfo.network.model.character.CharacterListData
import hu.mbarni.android.rickiversemortinfo.network.model.epiosde.EpisodeData
import hu.mbarni.android.rickiversemortinfo.network.model.epiosde.EpisodeListData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("/api/episode/{id}")
    fun getEpisode(@Path("id") id: Int): Call<EpisodeData>?

    @GET("/api/episode")
    fun getEpisodeList(@Query("page") path: String): Call<EpisodeListData>?

    @GET("/api/character/{id}")
    fun getCharacter(@Path("id") id: Int): Call<CharacterData>?

    @GET("/api/character")
    fun getCharacterList(@Query("page") path: String): Call<CharacterListData>?
}