package com.example.pokemons3.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pokemons3.R
import com.example.pokemons3.models.api.pokemon.Sprites


class PokemonSpritesAdapter(
    var context: Context,
    pokemonSprites: Sprites
) : RecyclerView.Adapter<PokemonSpritesAdapter.PokemonViewHolder>() {


    private var pokemonList = ArrayList<String>()

    init {
        pokemonList.add(pokemonSprites.back_default)
        pokemonList.add(pokemonSprites.back_shiny)
        pokemonList.add(pokemonSprites.front_default)
        pokemonList.add(pokemonSprites.front_shiny)
        pokemonList.add(pokemonSprites.other.dream_world.front_default)
        pokemonList.add(pokemonSprites.other.home.front_default)
        pokemonList.add(pokemonSprites.other.home.front_shiny)
//        pokemonList.add(pokemonSprites.other.official_artwork.front_default)
//        pokemonList.add(pokemonSprites.other.official_artwork.front_shiny)
    }


    class PokemonViewHolder(itemView: View,val context: Context) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView =
            itemView.findViewById(R.id.iv_pokemon_sprites)


        fun bind(sprite: String) {
            Log.d("TAG", "bind: $sprite")

            if (sprite.endsWith(".svg")) {
                loadSvgImage(sprite)
            } else {
                loadPngImage(sprite)
            }
        }

        private fun loadPngImage(sprite: String) {
            val options: RequestOptions = RequestOptions()
                .placeholder(R.drawable.baseline_catching_pokemon_24)
                .error(R.drawable.baseline_wifi_tethering_error_24)
            Glide.with(itemView)
                .load(sprite)
                .apply(RequestOptions.centerCropTransform())
                .apply(options)
                .into(imageView)
        }

        private fun loadSvgImage(sprite: String) {

            val imageLoader = ImageLoader.Builder(context)
                .componentRegistry { add(SvgDecoder(context)) }
                .build()

            val request = ImageRequest.Builder(context)
                .crossfade(true)
                .crossfade(500)
                .placeholder(com.example.pokemons3.R.drawable.baseline_catching_pokemon_24)
                .error(com.example.pokemons3.R.drawable.baseline_wifi_tethering_error_24)
                .data(sprite)
                .target(imageView)
                .build()

            imageLoader.enqueue(request)
        }



    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(com.example.pokemons3.R.layout.each_pokemon_sprites, parent, false)
        Log.d("TAG", "onCreateViewHolder:  ")



        return PokemonViewHolder(itemView,context)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.bind(pokemon)
//        holder.itemView.setOnClickListener {
//            onPokemonClickListener.onPokemonClick(pokemon.id)
//        }
//        holder.itemView.setBackgroundResource(util.typeToColor(pokemon.types[0].type.name))
    }


    override fun getItemCount(): Int {
        return pokemonList.size
    }

    //    private fun setBackgroundGradient(pokemon: CustomPokemonList, holder: PokemonViewHolder) {
//        val startColor = util.typeToColor(pokemon.types[0].type.name)
//        var endColor = Color.WHITE
//        if (pokemon.types.size == 2) {
//            endColor = util.typeToColor(pokemon.types[1].type.name)
//        }
//        val gd = makeGradient(holder.itemView, startColor, endColor)
//        holder.itemView.background = gd
//    }

//    fun addPokemon(pokemon: CustomPokemonList) {
//        pokemonSpritesList.add(pokemon)
//        notifyItemInserted(pokemonSpritesList.size)
//    }

//    fun makeGradient(view: View, startColor: Int, endColor: Int): GradientDrawable {
//        val gd = GradientDrawable(
//            GradientDrawable.Orientation.LEFT_RIGHT,
//            intArrayOf(startColor, endColor)
//        )
//        gd.cornerRadius = 0f
//        view.background = gd
//        return gd
//    }

//    interface PokemonClickListener {
//        fun onPokemonClick(id: Int)
//    }

}


//val requestOptions = RequestOptions()
//    .diskCacheStrategy(DiskCacheStrategy.NONE)
//    .error(R.drawable.baseline_wifi_tethering_error_24)
//
//val glideUrl = GlideUrl(sprite)
//
//Glide.with(itemView)
//.`as`(PictureDrawable::class.java)
//.apply(requestOptions)
//.load(glideUrl)
//.listener(object : RequestListener<PictureDrawable> {
//    override fun onLoadFailed(
//        e: GlideException?,
//        model: Any?,
//        target: Target<PictureDrawable>?,
//        isFirstResource: Boolean
//    ): Boolean {
//        // Handle the failure case here
//        return false
//    }
//
//    override fun onResourceReady(
//        resource: PictureDrawable?,
//        model: Any?,
//        target: Target<PictureDrawable>?,
//        dataSource: DataSource?,
//        isFirstResource: Boolean
//    ): Boolean {
//        if (resource != null) {
//            imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null) // Enable hardware acceleration
//            imageView.setImageDrawable(resource)
//        }
//        return false
//    }
//})
//.into(object : ImageViewTarget<PictureDrawable>(imageView) {
//    override fun setResource(resource: PictureDrawable?) {
//        if (resource != null) {
//            imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null) // Enable hardware acceleration
//            imageView.setImageDrawable(resource)
//        }
//    }
//})


//                Log.d("TAG", "bind: $sprite")
//                val url = sprite
//                val glideUrl = GlideUrl(url)
//
//                val options: RequestOptions = RequestOptions()
//                    .placeholder(R.drawable.baseline_catching_pokemon_24)
//                    .error(R.drawable.baseline_wifi_tethering_error_24)
//
//                Glide.with(itemView)
//                    .load(glideUrl)
//                    .apply(RequestOptions.centerCropTransform())
//                    .apply(options)
//                    .into(imageView)

//            Glide.with(itemView)
//                .as(PictureDrawable::class.java)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .load(sprite)
//                .listener(object : SvgSoftwareLayerSetter() {
//                    override fun onSvgLoaded(svg: SVG?) {
//                        if (svg != null) {
//                            val pictureDrawable = PictureDrawable(svg.renderToPicture())
//                            imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null) // Enable hardware acceleration
//                            imageView.setImageDrawable(pictureDrawable)
//                        }
//                    }
//                })
//                .into(imageView)

