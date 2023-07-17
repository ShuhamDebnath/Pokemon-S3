package com.example.pokemons3.models.api.machine

data class Machine(
    val id: Int,
    val item: Item,
    val move: Move,
    val version_group: VersionGroup
)