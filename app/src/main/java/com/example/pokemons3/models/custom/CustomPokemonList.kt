package com.example.pokemons3.models.custom

import com.example.pokemons3.models.api.pokemon.Type

data class CustomPokemonList(
    val id: Int,
    val name: String,
    val image: String,
    val url: String,
    val types: List<Type>
    )