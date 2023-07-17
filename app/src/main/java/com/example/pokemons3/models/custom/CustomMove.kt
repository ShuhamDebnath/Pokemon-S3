package com.example.pokemons3.models.custom

import com.example.pokemons3.models.api.move.DamageClass
import com.example.pokemons3.models.api.move.LearnedByPokemon
import com.example.pokemons3.models.api.move.Machine
import com.example.pokemons3.models.api.move.Type

class CustomMove(
    val accuracy: Int,
    val damage_class: DamageClass,
    val id: Int,
    val machines: List<Machine>,
    val name: String,
    val power: Int,
    val pp: Int,
    val priority: Int,
    val type: Type,
    var levelLearned : Int = 0
    )