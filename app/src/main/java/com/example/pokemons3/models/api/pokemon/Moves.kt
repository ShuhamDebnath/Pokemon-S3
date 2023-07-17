package com.example.pokemons3.models.api.pokemon

data class Moves(
    val move: Move,
    val version_group_details: List<VersionGroupDetail>
)