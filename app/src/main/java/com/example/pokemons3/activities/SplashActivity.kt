package com.example.pokemons3.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemons3.R
import com.example.pokemons3.viewModels.PokemonViewModel
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    private val viewModel: PokemonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


//        val viewModel = PokemonViewModel(application)
        viewModel.fetchFewPokemonList()

        viewModel.getCustomPokemonList().observe(this) {
            if (it.size == 20) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        val scheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        scheduledExecutorService.schedule(
            {
                if (viewModel.getCustomPokemonList().value?.size == 0){
                    Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show()
                }
                scheduledExecutorService.schedule(
                    {
                        finish()
                    },2,TimeUnit.SECONDS)

            },
            10, TimeUnit.SECONDS
        )
    }
}