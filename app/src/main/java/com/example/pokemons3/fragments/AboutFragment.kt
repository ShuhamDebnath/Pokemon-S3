package com.example.pokemons3.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pokemons3.R
import com.example.pokemons3.databinding.FragmentAboutBinding
import com.example.pokemons3.utils.util
import com.example.pokemons3.viewModels.PokemonViewModel

class AboutFragment : Fragment() {

    lateinit var binding: FragmentAboutBinding
    val viewModel: PokemonViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

//    base_experience
//    abilities
//    height
//    weight
//    abilities


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutBinding.inflate(inflater, container, false)

        Log.d("TAG", "onCreateView: AboutFragment")



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("TAG", "onViewCreated: AboutFragment")

        val id = viewModel.getId()
        viewModel.getEachPokemonList().observe(viewLifecycleOwner) {
            val pokemon = viewModel.getEachPokemonList().value?.get(id)
            Log.d("TAG", "onCreateView: pokemon : $pokemon ")

            if( pokemon!= null){
                binding.tvName.text = pokemon.name
                binding.tvId.text = "#"+pokemon.id
                val types = pokemon.types
                binding.cvType1.visibility = View.VISIBLE
                binding.tvType1.text = types[0].type.name
                val startColor1 = ContextCompat.getColor(requireContext(),
                    util.typeToColor(pokemon.types[0].type.name)
                )
//                var endColor = ContextCompat.getColor(requireContext(), R.color.white)

//                binding.tvType1.setBackgroundResource(R.color.fire)
//                binding.cvType1.setBackgroundColor(resources.getColor(R.color.fire))
//                util.setBackgroundGradient2(binding.tvType1, startColor1)
                if(types.size == 2){
                    binding.cvType2.visibility = View.VISIBLE
                    binding.tvType2.text = types[1].type.name
//                    val startColor2 = ContextCompat.getColor(requireContext(),
//                        util.typeToColor(pokemon.types[1].type.name)
//                    )
//                    var endColor = ContextCompat.getColor(requireContext(), R.color.white)
//                    util.setBackgroundGradient(binding.tvType2, startColor2, endColor)
                }



                val height: Double = pokemon.height.toDouble()/10
                val weight: Double = pokemon.weight.toDouble()/10
                binding.tvHeightValue.text = height.toString()+" m"
                binding.tvWeightValue.text = weight.toString()+" Kg"

            }
        }


    }

    fun loadPngImage(url: String, ivPokemonImage: ImageView) {
        Glide.with(requireActivity())
            .load(url)
            .apply(RequestOptions.centerCropTransform())
            .placeholder(R.drawable.baseline_catching_pokemon_24)
            .into(ivPokemonImage)
    }


}