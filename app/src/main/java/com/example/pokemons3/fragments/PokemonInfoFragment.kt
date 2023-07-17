package com.example.pokemons3.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.pokemons3.R
import com.example.pokemons3.databinding.FragmentPokemonInfoBinding
import com.example.pokemons3.utils.util
import com.example.pokemons3.utils.util.setBackgroundGradient
import com.example.pokemons3.viewModels.PokemonViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class PokemonInfoFragment : Fragment() {

    private lateinit var binding: FragmentPokemonInfoBinding
    private val viewModel: PokemonViewModel by activityViewModels()
    private lateinit var bottomNavigationView: BottomNavigationView
    private var id = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPokemonInfoBinding.inflate(inflater, container, false)
        id = viewModel.getId()
        Log.d("TAG", "onCreateView: ${viewModel.getEachPokemonList().value?.containsKey(id)} ")
        if (viewModel.getEachPokemonList().value?.containsKey(id) == false || viewModel.getEachPokemonList().value?.containsKey(
                id
            ) == null
        ) {
            viewModel.fetchPokemon(id)
        }


        bottomNavigationView = binding.bottomNavigationView

        // Create fragments for each of the bottom navigation items.

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aboutFragment = AboutFragment()
        val baseStatsFragment = BaseStatsFragment()
        val movesFragment = MovesFragment()
        val spritesFragment = SpritesFragment()

        // Set the onNavigationItemSelectedListener for the BottomNavigationView.
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.about -> {
                    // Switch to the HomeFragment.
                    fragmentManager?.beginTransaction()
                        ?.replace(R.id.fragment_container_view, aboutFragment)?.commit()
                }

                R.id.base_stats -> {
                    // Switch to the ProfileFragment.
                    fragmentManager?.beginTransaction()
                        ?.replace(R.id.fragment_container_view, baseStatsFragment)?.commit()
                }

                R.id.moves -> {
                    // Switch to the SettingsFragment.
                    fragmentManager?.beginTransaction()
                        ?.replace(R.id.fragment_container_view, movesFragment)?.commit()
                }

                R.id.sprites -> {
                    // Switch to the SettingsFragment.
                    fragmentManager?.beginTransaction()
                        ?.replace(R.id.fragment_container_view, spritesFragment)?.commit()
                }
            }
            true
        }

        // Set the currently selected bottom navigation item.
        bottomNavigationView.setSelectedItemId(R.id.about)

        viewModel.getEachPokemonList().observe(viewLifecycleOwner) {
            val pokemon = viewModel.getEachPokemonList().value?.get(id)
            if (pokemon != null) {

                val startColor = ContextCompat.getColor(
                    requireContext(),
                    util.typeToColor(pokemon.types[0].type.name)
                )
                var endColor = ContextCompat.getColor(requireContext(), R.color.white)

                setBackgroundGradient(binding.root, startColor, endColor)
                loadSvgImage(pokemon.sprites.other.dream_world.front_default)


                pokemon.moves.forEach { move ->
                    val moveId = urlToId(move.move.url)
                    val method = move.version_group_details[0].move_learn_method.name
                    val moveLearnedLevel = move.version_group_details[0].level_learned_at
                    viewModel.fetchCustomMove(moveId, method, moveLearnedLevel)
                }


            }
            Log.d("TAG", "onViewCreated: $pokemon")
        }


    }

    private fun loadSvgImage(sprite: String) {

        val imageLoader = ImageLoader.Builder(requireContext())
            .componentRegistry { add(SvgDecoder(requireContext())) }
            .build()

        val request = ImageRequest.Builder(requireContext())
            .crossfade(true)
            .crossfade(500)
            .placeholder(R.drawable.baseline_catching_pokemon_24)
            .error(R.drawable.baseline_wifi_tethering_error_24)
            .data(sprite)
            .target(binding.ivPokemonImage)
            .build()

        imageLoader.enqueue(request)
    }

    fun urlToId(url: String): Int {
        var id = "";
        var remUrl = url.dropLast(1)
        remUrl = remUrl.substring(remUrl.lastIndexOf('/'))
        remUrl.forEach {
            if (it.isDigit()) id += it
        }
        return id.toInt()
    }
}