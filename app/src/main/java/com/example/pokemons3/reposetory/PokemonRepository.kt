package com.example.pokemons3.reposetory

import com.example.pokemons3.apiService.ApiService
import com.example.pokemons3.models.api.pokemon.Pokemon
import com.example.pokemons3.models.api.pokemon.PokemonList
import com.example.pokemons3.models.custom.CustomMove
import com.example.pokemons3.models.custom.FewPokemonDetails
import retrofit2.Response

class PokemonRepository(private val apiService: ApiService) {


    suspend fun getPokemonList( offset: Int, limit: Int): Response<PokemonList>{
        return apiService.getPokemonList(offset,limit)
    }

    suspend fun getPokemonFewDetails( name: String): Response<FewPokemonDetails>{
        return apiService.getPokemonFewDetails(name)
    }

    suspend fun getPokemonInfo( id: Int): Response<Pokemon>{
        return apiService.getPokemonInfo(id)
    }

    suspend fun getCustomMove(id: Int): Response<CustomMove>{
        return apiService.getCustomMove(id)
    }




}