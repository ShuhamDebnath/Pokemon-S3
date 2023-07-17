package com.example.pokemons3.utils

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.pokemons3.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object util {
    const val BASE_URL = "https://pokeapi.co/api/v2/";

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun typeToColor(type: String): Int {
        when (type) {
            "bug" -> return R.color.bug
            "dark" -> return R.color.dark
            "dragon" -> return R.color.dragon
            "electric" -> return R.color.electric
            "fairy" -> return R.color.fairy
            "fighting" -> return R.color.fighting
            "fire" -> return R.color.fire
            "flying" -> return R.color.flying
            "ghost" -> return R.color.ghost
            "grass" -> return R.color.grass
            "ground" -> return R.color.ground
            "ice" -> return R.color.ice
            "normal" -> return R.color.normal
            "psychic" -> return R.color.psychic
            "poison" -> return R.color.poison
            "rock" -> return R.color.rock
            "steel" -> return R.color.steel
            "colorless" -> return R.color.colorless
            "water" -> return R.color.water
            else -> {
                return R.color.white
            }
        }
    }

    fun setBackgroundGradient(view: View, startColor: Int, endColor: Int) {
        Log.d("TAG", "setBackgroundGradient: startColor: ${startColor}   endColor $endColor")
        // Create a GradientDrawable with the two colors.
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(startColor, endColor)
        )
        // Set the background of the view to the gradient.
        view.background = gradientDrawable
    }
    fun setBackgroundGradient2(view: View, midColor: Int) {
        // Create a GradientDrawable with the two colors.
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(R.color.white, midColor, R.color.white)
        )
        // Set the background of the view to the gradient.
        view.background = gradientDrawable
    }
}

