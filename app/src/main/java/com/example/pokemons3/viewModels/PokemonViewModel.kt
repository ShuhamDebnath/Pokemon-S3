package com.example.pokemons3.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokemons3.apiService.ApiService
import com.example.pokemons3.models.api.pokemon.Pokemon
import com.example.pokemons3.models.custom.CustomMove
import com.example.pokemons3.models.custom.CustomPokemonList
import com.example.pokemons3.reposetory.PokemonRepository
import com.example.pokemons3.utils.util
import kotlinx.coroutines.launch
import java.util.Locale

class PokemonViewModel(private val application: Application) : AndroidViewModel(application) {

    private var customPokemonList = MutableLiveData<ArrayList<CustomPokemonList>>()
    private var eachPokemonList = MutableLiveData<HashMap<Int, Pokemon>>()
    private var apiService = util.getInstance().create(ApiService::class.java)
    private val repository = PokemonRepository(apiService)
    private var filteredList: List<CustomPokemonList> = ArrayList()
    var isLoading = false
    private var offset = 0
    private val limit = 20
    private var id = 0;
    private var searchQuery: String = ""
    var isSearching = false


    //    val customMove = MutableLiveData<CustomMove>()
//    val customMovesList = MutableLiveData<HashMap<CustomMove, String>>()
    private val movesLevelUp = MutableLiveData<ArrayList<CustomMove>?>()
    private val movesTutor = MutableLiveData<ArrayList<CustomMove>?>()
    private val movesMachine = MutableLiveData<ArrayList<CustomMove>?>()
    private val movesRemaining = MutableLiveData<ArrayList<CustomMove>?>()

    init {
        Log.d("TAG", "PokemonViewModel: Started ")
        fetchFewPokemonList()
    }


    fun getMovesLevelUpLiveData(): MutableLiveData<ArrayList<CustomMove>?> {
        Log.d("TAG", "getMovesLevelUpLiveData: ")
        return movesLevelUp
    }

    fun getMovesTutorLiveData(): MutableLiveData<ArrayList<CustomMove>?> {
        Log.d("TAG", "getMovesTutorLiveData: ")
        return movesTutor
    }

    fun getMovesMachineLiveData(): MutableLiveData<ArrayList<CustomMove>?> {
        Log.d("TAG", "getMovesMachineLiveData: ")
        return movesMachine
    }

    fun getMovesRemainingLiveData(): MutableLiveData<ArrayList<CustomMove>?> {
        Log.d("TAG", "getMovesRemainingLiveData: ")
        return movesRemaining
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getId(): Int {
        return id
    }

    fun setSearchQuery(query: String) {
        searchQuery = query
    }

    fun getSearchQuery(): String {
        return searchQuery
    }

    fun setFilteredList(list: List<CustomPokemonList>?) {
        if (list != null) {
            filteredList = list
        }
    }

    fun getFilteredList(): List<CustomPokemonList> {
        return filteredList
    }

    fun getCustomPokemonList(): MutableLiveData<ArrayList<CustomPokemonList>> {
        return customPokemonList
    }

    fun getEachPokemonList(): MutableLiveData<HashMap<Int, Pokemon>> {
        return eachPokemonList
    }

    fun searchTask(searchText: String): List<CustomPokemonList>? {
        Log.d("TAG", "searchTask: ")

        val filteredList = customPokemonList.value?.filter { pokemon ->

            pokemon.name.lowercase(Locale.ROOT).contains(searchText.lowercase(Locale.ROOT)) ||
                    pokemon.id.toString().lowercase(Locale.ROOT)
                        .contains(searchText.lowercase(Locale.ROOT)) ||
                    pokemon.types[0].type.name.lowercase(Locale.ROOT)
                        .contains(searchText.lowercase(Locale.ROOT)) ||
                    pokemon.types[pokemon.types.size - 1].type.name.lowercase(Locale.ROOT)
                        .contains(searchText.lowercase(Locale.ROOT))
        }
        return filteredList
    }

    private fun addToCustomPokemon(pokemon: CustomPokemonList) {
//        Log.d("TAG", "addToCustomPokemon: $pokemon")
        var currentList = customPokemonList.value
        val newList = ArrayList<CustomPokemonList>()
        newList.add(pokemon)
        if (currentList == null) {
            currentList = newList
        } else {
            currentList.addAll(newList)
        }
        customPokemonList.value = currentList!!
    }

    private fun addToEachPokemon(pokemon: Pokemon) {
        var currentList = eachPokemonList.value
        val newList = HashMap<Int, Pokemon>()
        newList.put(pokemon.id, pokemon)
        if (currentList == null) {
            currentList = newList
        } else {
            currentList.putAll(newList)
        }
        eachPokemonList.value = currentList!!
    }

    fun fetchFewPokemonList() {
        Log.d("TAG", "fetchPokemonList: called")
        isLoading = true

        viewModelScope.launch {
            try {
                val response = repository.getPokemonList(offset, limit)
                if (response.isSuccessful) {
                    Log.d("TAG", "fetchPokemonList: response1")
                    val pokemonListResponse = response.body()
                    if (pokemonListResponse != null) {
//                        Log.d("TAG", "fetchPokemonList: ${response.body()!!.results}")

                        for (pokemon in pokemonListResponse.results) {
//                            Log.d("TAG", "fetchPokemon: $pokemon")
                            val name = pokemon.name
                            val url = pokemon.url
//                            Log.d("TAG", "fetchPokemon: response1 ${name} ${url}")
                            val detailsResponse = repository.getPokemonFewDetails(name)
                            if (detailsResponse.isSuccessful) {
//                                Log.d("TAG", "fetchPokemon: response2")
//                                Log.d("TAG", "fetchPokemon: ${detailsResponse.body()}")
                                val details = detailsResponse.body()
                                if (details != null) {

                                    val id = details.id
                                    val image = details.sprites.front_default
                                    val list = details.types
                                    val customPokemon =
                                        CustomPokemonList(id, name, image, url, list)

                                    addToCustomPokemon(customPokemon)
                                }
                            }
                        }
                        offset += limit
                    }
                } else {
//                    Toast.makeText(
//                        application,
//                        "Failed to fetch Pokemon" + response.message(),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    Log.d("TAG", "fetchPokemon: Failed to fetch Pokemon" + response.message())
                }
            } catch (e: Exception) {

//                Toast.makeText(
//                    application,
//                    "Failed to fetch Pokemon 1 " + e.localizedMessage,
//                    Toast.LENGTH_SHORT
//                ).show()
                Log.d("TAG", "fetchPokemon: Failed to fetch Pokemon 2 " + e.localizedMessage)
            }

            isLoading = false
        }
    }

    fun fetchPokemon(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getPokemonInfo(id)
                if (response.isSuccessful) {

                    val pokemon = response.body()
                    if (pokemon != null) {
                        Log.d("TAG", "fetchPokemon: ${pokemon.name}")

                        val myPokemon = Pokemon(
                            pokemon.abilities,
                            pokemon.base_experience,
                            pokemon.forms,
                            pokemon.game_indices,
                            pokemon.height,
                            pokemon.held_items,
                            pokemon.id,
                            pokemon.is_default,
                            pokemon.location_area_encounters,
                            pokemon.moves,
                            pokemon.name,
                            pokemon.order,
                            pokemon.past_types,
                            pokemon.species,
                            pokemon.sprites,
                            pokemon.stats,
                            pokemon.types,
                            pokemon.weight
                        )

//                        val tempPokemonList = ArrayList<Pokemon>()
//                        tempPokemonList.add(myPokemon)
                        addToEachPokemon(myPokemon)

                    } else {
                        Log.d("TAG", "fetchPokemon: pokemon = null")
                    }


                } else {
                    Log.d("TAG", "fetchPokemon: response error ${response.message()}")
                }
            } catch (e: Error) {
                Log.d("TAG", "fetchPokemon: error ${e.localizedMessage}")
            }

        }
    }

    fun fetchCustomMove(id: Int, method: String, moveLearnedLevel: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getCustomMove(id)
                if (response.isSuccessful) {
                    val move = response.body()
                    if (move != null) {

//                        if(movesLevelUp.value == null){
//                            movesLevelUp.value = ArrayList()
//                        }
//                        if(movesTutor.value == null){
//                            movesTutor.value = ArrayList()
//                        }
//                        if(movesMachine.value == null){
//                            movesMachine.value = ArrayList()
//                        }
//                        if(movesRemaining.value == null){
//                            movesRemaining.value = ArrayList()
//                        }

                        if (moveLearnedLevel != 0) {
                            move.levelLearned = moveLearnedLevel
                        }
                        Log.d("TAG", "fetchCustomMove: $method")
                        Log.d("TAG", "fetchCustomMove: ${move.name}")

                        when (method) {
                            "level-up" -> {

                                val list = if (movesLevelUp.value != null) {
                                    movesLevelUp.value
                                } else {

                                    ArrayList()
                                }
                                list!!.add(move)
                                movesLevelUp.value = list

                                Log.d(
                                    "TAG",
                                    "fetchCustomMove: movesLevelUp ${movesLevelUp.value?.size}"
                                )
                            }

                            "tutor" -> {
                                val list = if (movesTutor.value != null) {
                                    movesTutor.value
                                } else {

                                    ArrayList()
                                }
                                list!!.add(move)
                                movesTutor.value = list
                                Log.d(
                                    "TAG",
                                    "fetchCustomMove: movesTutor ${movesTutor.value?.size}"
                                )
                            }

                            "machine" -> {
                                val list = if (movesMachine.value != null) {
                                    movesMachine.value
                                } else {

                                    ArrayList()
                                }
                                list!!.add(move)
                                movesMachine.value = list

                                Log.d(
                                    "TAG",
                                    "fetchCustomMove: movesMachine ${movesMachine.value?.size}"
                                )
                            }

                            else -> {
                                val list = if (movesRemaining.value != null) {
                                    movesRemaining.value
                                } else {

                                    ArrayList()
                                }
                                list!!.add(move)
                                movesRemaining.value = list

                                Log.d(
                                    "TAG",
                                    "fetchCustomMove: movesRemaining ${movesRemaining.value?.size}"
                                )
                            }
                        }


                    }
                } else {
                    Log.d("TAG", "fetchCustomMove: response error ${response.message()}")
                }
            } catch (e: Error) {
                Log.d("TAG", "fetchCustomMove: error ${e.localizedMessage}")
            }
        }
    }
}