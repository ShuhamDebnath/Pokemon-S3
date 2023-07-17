package com.example.pokemons3.fragments

import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemons3.R
import com.example.pokemons3.adapters.PokemonSpritesAdapter
import com.example.pokemons3.databinding.FragmentPokemonInfoBinding
import com.example.pokemons3.databinding.FragmentSpritesBinding
import com.example.pokemons3.viewModels.PokemonViewModel

class SpritesFragment : Fragment() {

    private lateinit var binding: FragmentSpritesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonSpritesAdapter
    private val viewModel :  PokemonViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG", "onCreate: SpritesFragment ")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSpritesBinding.inflate(inflater,container,false)
        recyclerView = binding.rvPokeSprites
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sprites = viewModel.getEachPokemonList().value?.get(viewModel.getId())?.sprites
        if(sprites!= null){
            adapter = PokemonSpritesAdapter(requireContext(),sprites)
        }
        recyclerView.adapter = adapter

        adapter.notifyItemRangeInserted(0,9)

    }

}