package com.example.pokemons3.models.api.pokemon

data class PokemonList(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Result>
)