package com.example.pokemons3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemons3.databinding.EachMovesBinding
import com.example.pokemons3.models.custom.CustomMove
import okhttp3.internal.notify

class MovesListAdapter() : RecyclerView.Adapter<MovesListAdapter.ViewHolder>() {
    private val movesList = ArrayList<CustomMove>()
    private lateinit var binding: EachMovesBinding

    class ViewHolder(binding: EachMovesBinding) : RecyclerView.ViewHolder(binding.root) {

        val level: TextView = binding.tvLevel
        val name: TextView = binding.tvName
        val type: TextView = binding.tvType
        val category: TextView = binding.tvCategory
        val power: TextView = binding.tvPower
        val acc: TextView = binding.tvAccuracy
        val pp: TextView = binding.tvPp

        fun bind(moves: CustomMove) {
            level.text = moves.levelLearned.toString()
            name.text = moves.name
            type.text = moves.type.name
            category.text = moves.damage_class.name
            power.text = moves.power.toString()
            acc.text = moves.accuracy.toString()
            pp.text = moves.pp.toString()
        }

    }

    fun updateMovesList(moves: List<CustomMove>) {
        movesList.clear()
        movesList.addAll(moves)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = EachMovesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val moves = movesList[position]
        holder.bind(moves)
    }
}