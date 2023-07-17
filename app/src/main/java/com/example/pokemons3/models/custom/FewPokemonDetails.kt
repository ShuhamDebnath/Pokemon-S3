package com.example.pokemons3.models.custom
import com.example.pokemons3.models.api.pokemon.Type

data class FewPokemonDetails(
    val sprites: PokemonSprites,
    val id: Int,
    val types: List<Type>,
)

