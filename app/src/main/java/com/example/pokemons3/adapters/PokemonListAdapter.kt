package com.example.pokemons3.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pokemons3.R
import com.example.pokemons3.models.custom.CustomPokemonList
import com.example.pokemons3.utils.util.setBackgroundGradient
import com.example.pokemons3.utils.util.typeToColor

class PokemonListAdapter(
    val context: Context,
    private val onPokemonClickListener: PokemonClickListener
) : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {


    private var pokemonList = ArrayList<CustomPokemonList>()
//    lateinit var binding : EachPokemonItemBinding


    fun updatePokemonList(list: ArrayList<CustomPokemonList>) {
        if (pokemonList.isEmpty()) {
            pokemonList.addAll(list)
            notifyItemRangeInserted(0, list.size-1)
        } else {
            val lastIndex = list.size - 1
            pokemonList.add(list[lastIndex])
            notifyItemInserted(lastIndex)
        }

    }

    fun updateList(list: ArrayList<CustomPokemonList>) {
        Log.d("TAG", "updateList: ")
        pokemonList.clear()
        pokemonList.addAll(list)
        notifyDataSetChanged()
    }


    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tv_name)
        private val imageView: ImageView = itemView.findViewById(R.id.iv_pokemon)
        private val id: TextView = itemView.findViewById(R.id.tv_id)
        private val type1: TextView = itemView.findViewById(R.id.cv_type1)
        private val type2: TextView = itemView.findViewById(R.id.cv_type2)
        private val layout: ConstraintLayout = itemView.findViewById(R.id.cl_background)


        fun bind(pokemon: CustomPokemonList, context: Context) {
            nameTextView.text = pokemon.name
            Glide.with(itemView)
                .load(pokemon.image)
                .apply(RequestOptions.centerCropTransform())
                .placeholder(R.drawable.baseline_catching_pokemon_24)
                .into(imageView)

            id.text = "#${pokemon.id}"
            if (pokemon.types.isNotEmpty()) {
                type1.visibility = View.VISIBLE
                type1.text = pokemon.types[0].type.name
            }
            if (pokemon.types.size == 2) {
                type2.visibility = View.VISIBLE
                type2.text = pokemon.types[1].type.name
            }

            val startColor = ContextCompat.getColor(context, typeToColor(pokemon.types[0].type.name))
            var endColor = ContextCompat.getColor(context, R.color.white)
            setBackgroundGradient(layout, startColor, endColor)


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.each_pokemon_item, parent, false)
        Log.d("TAG", "onCreateViewHolder: ")
        return PokemonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.bind(pokemon, context)
        holder.itemView.setOnClickListener {
            onPokemonClickListener.onPokemonClick(pokemon.id)
        }
    }


    override fun getItemCount() = pokemonList.size

    fun addPokemon(pokemon: CustomPokemonList) {
        pokemonList.add(pokemon)
        notifyItemInserted(pokemonList.size)
    }


    interface PokemonClickListener {
        fun onPokemonClick(id: Int)
    }

}