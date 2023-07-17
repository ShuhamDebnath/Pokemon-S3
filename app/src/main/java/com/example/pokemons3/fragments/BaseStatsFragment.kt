package com.example.pokemons3.fragments

import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.StreamEncoder
import com.example.pokemons3.R
import com.example.pokemons3.databinding.FragmentBaseStatsBinding
import com.example.pokemons3.viewModels.PokemonViewModel
import java.io.InputStream


class BaseStatsFragment : Fragment() {


    lateinit var binding : FragmentBaseStatsBinding
    private val viewModel : PokemonViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBaseStatsBinding.inflate(inflater,container,false)


        val id = viewModel.getId()
        val pokemonStats = viewModel.getEachPokemonList().value?.get(id)?.stats

        Log.d("TAG", "onCreateView: $pokemonStats")
        if(pokemonStats != null){
            binding.apply {

                val hp = pokemonStats[0].base_stat
                tvValueHp.text = hp.toString()
                pbHp.progress = hp.toFloat()/250;

                val attack = pokemonStats[1].base_stat
                tvValueAttack.text = attack.toString()
                pbAttack.progress = attack.toFloat()/250;

                val defence = pokemonStats[2].base_stat
                tvValueDefence.text = defence.toString()
                pbDefence.progress = defence.toFloat()/250;

                val spAtt = pokemonStats[3].base_stat
                tvValueSpAtt.text = spAtt.toString()
                pbSpAtt.progress = spAtt.toFloat()/250;

                val spDef = pokemonStats[4].base_stat
                tvValueSpDef.text = spDef.toString()
                pbSpDef.progress = spDef.toFloat()/250;

                val speed = pokemonStats[5].base_stat
                tvValueSpeed.text = speed.toString()
                pbSpeed.progress = speed.toFloat()/250;

                val total = hp + attack + defence + spAtt + spDef + speed
                tvValueTotal.text = total.toString()
                pbTotal.progress = total.toFloat()/1000;

            }

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the maximum value of the progress bar

    }




}