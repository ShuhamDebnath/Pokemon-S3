package com.example.pokemons3.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemons3.R
import com.example.pokemons3.adapters.MovesListAdapter
import com.example.pokemons3.databinding.FragmentMovesBinding
import com.example.pokemons3.models.custom.CustomMove
import com.example.pokemons3.viewModels.PokemonViewModel


class MovesFragment : Fragment() {

    private lateinit var binding: FragmentMovesBinding
    private val viewModel: PokemonViewModel by activityViewModels()

    //    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterLevelUp: MovesListAdapter
    private lateinit var adapterTutor: MovesListAdapter
    private lateinit var adapterMachine: MovesListAdapter

    private lateinit var movesLevelUp: ArrayList<CustomMove>
    private lateinit var movesTutor: ArrayList<CustomMove>
    private lateinit var movesMachine: ArrayList<CustomMove>
    private lateinit var movesRemaining: ArrayList<CustomMove>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMovesBinding.inflate(inflater, container, false)
        Log.d("TAG", "onCreateView: MovesFragment created")


        movesLevelUp = ArrayList()
        movesTutor = ArrayList()
        movesMachine = ArrayList()
        movesRemaining = ArrayList()




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterLevelUp = MovesListAdapter()
        adapterTutor = MovesListAdapter()
        adapterMachine = MovesListAdapter()

        binding.rvLevelUp.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterLevelUp
        }

        binding.rvTutor.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterTutor
        }

        binding.rvMachine.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterMachine
        }


        viewModel.getMovesLevelUpLiveData().observe(viewLifecycleOwner) { moves ->
            Log.d("TAG", "onViewCreated: observer  getMovesLevelUpLiveData")
            if (moves != null) {
                adapterLevelUp.updateMovesList(moves)
            }
        }

        viewModel.getMovesTutorLiveData().observe(viewLifecycleOwner) { moves ->
            Log.d("TAG", "onViewCreated: observer  getMovesTutorLiveData")
            if (moves != null) {
                adapterTutor.updateMovesList(moves)
            }
        }

        viewModel.getMovesMachineLiveData().observe(viewLifecycleOwner) { moves ->
            Log.d("TAG", "onViewCreated: observer  getMovesMachineLiveData")
            if (moves != null) {
                adapterMachine.updateMovesList(moves)
            }
        }

        binding.ivLevelUp.setOnClickListener {
            if (binding.rvLevelUp.visibility == View.GONE) {
                binding.ivLevelUp.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                binding.rvLevelUp.visibility = View.VISIBLE
                binding.tvInfoLevelUp.visibility = View.VISIBLE

            } else {
                binding.ivLevelUp.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                binding.rvLevelUp.visibility = View.GONE
                binding.tvInfoLevelUp.visibility = View.GONE
            }
        }
        binding.ivTutor.setOnClickListener {
            if (binding.rvTutor.visibility == View.GONE) {
                binding.ivTutor.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                binding.rvTutor.visibility = View.VISIBLE
                binding.tvInfoTutor.visibility = View.VISIBLE

            } else {
                binding.ivTutor.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                binding.rvTutor.visibility = View.GONE
                binding.tvInfoTutor.visibility = View.GONE
            }
        }
        binding.ivMachine.setOnClickListener {
            if (binding.rvMachine.visibility == View.GONE) {
                binding.ivMachine.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                binding.rvMachine.visibility = View.VISIBLE
                binding.tvInfoMachine.visibility = View.VISIBLE

            } else {
                binding.ivMachine.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                binding.rvMachine.visibility = View.GONE
                binding.tvInfoMachine.visibility = View.GONE
            }
        }

    }

}