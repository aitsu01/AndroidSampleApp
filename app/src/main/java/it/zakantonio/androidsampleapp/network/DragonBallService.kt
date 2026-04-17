package it.zakantonio.androidsampleapp.network

import it.zakantonio.androidsampleapp.model.Character
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DragonBallService {

    @GET("characters")
    suspend fun getCharacters(): CharactersResponse

    @GET("characters")
    suspend fun getCharactersByRace(@Query("race") race: String): List<Character>

    @GET("characters/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Character

}