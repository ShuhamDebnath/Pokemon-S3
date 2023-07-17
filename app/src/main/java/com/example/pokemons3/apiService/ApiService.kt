package com.example.pokemons3.apiService

import com.example.pokemons3.models.api.pokemon.Pokemon
import com.example.pokemons3.models.api.pokemon.PokemonList
import com.example.pokemons3.models.custom.CustomMove
import com.example.pokemons3.models.custom.FewPokemonDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<PokemonList>

    @GET("pokemon/{name}")
    suspend fun getPokemonFewDetails(@Path("name") name: String): Response<FewPokemonDetails>


    @GET("pokemon/{id}")
    suspend fun getPokemonInfo(@Path("id") id: Int): Response<Pokemon>

//    https://pokeapi.co/api/v2/move/5/

    @GET("move/{id}")
    suspend fun getCustomMove(@Path("id") id: Int): Response<CustomMove>

//    @GET("machine/{id}")
//    suspend fun getPokemonMachine(@Path("id") id: Int): Response<Machine>

//    @GET("api/users/")
//    fun getAllUser() : Call<User>
//
//    @GET("api/users/{id}")
//    suspend fun getUserById(@Path("id") id : String) : Response<UserX>
}