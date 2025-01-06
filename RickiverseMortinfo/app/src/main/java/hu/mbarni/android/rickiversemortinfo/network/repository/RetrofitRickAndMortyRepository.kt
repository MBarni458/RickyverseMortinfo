package hu.mbarni.android.rickiversemortinfo.network.repository

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import hu.mbarni.android.rickiversemortinfo.network.RickAndMortyApi
import hu.mbarni.android.rickiversemortinfo.network.model.character.CharacterData
import hu.mbarni.android.rickiversemortinfo.network.model.character.CharacterListData
import hu.mbarni.android.rickiversemortinfo.network.model.epiosde.EpisodeData
import hu.mbarni.android.rickiversemortinfo.network.model.epiosde.EpisodeListData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class RetrofitRickAndMortyRepository @Inject constructor() : IRickAndMortyRepository {
    private val retrofit: Retrofit
    private val rickAndMortyApi: RickAndMortyApi

    companion object {
        private const val SERVICE_URL = "https://rickandmortyapi.com/"
    }

    init {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        rickAndMortyApi = retrofit.create(RickAndMortyApi::class.java)
    }

    override suspend fun getEpisode(id: Int): Call<EpisodeData>? {
        return rickAndMortyApi.getEpisode(id)
    }

    override suspend fun getEpisodeList(path: String): Call<EpisodeListData>? {
        return rickAndMortyApi.getEpisodeList(path)
    }

    override suspend fun getCharacter(id: Int): Call<CharacterData>? {
        return rickAndMortyApi.getCharacter(id)
    }

    override suspend fun getCharacterList(path: String): Call<CharacterListData>? {
        return rickAndMortyApi.getCharacterList(path)
    }
}