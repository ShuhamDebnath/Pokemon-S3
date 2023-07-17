package com.example.pokemons3.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemons3.R
import com.example.pokemons3.adapters.PokemonListAdapter
import com.example.pokemons3.databinding.FragmentPokemonListBinding
import com.example.pokemons3.models.custom.CustomPokemonList
import com.example.pokemons3.viewModels.PokemonViewModel


class PokemonListFragment : Fragment(), PokemonListAdapter.PokemonClickListener {

    private lateinit var context: Context
    private lateinit var binding: FragmentPokemonListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var searchView: EditText
    private var pokemonList: ArrayList<CustomPokemonList> = ArrayList()
    private val viewModel: PokemonViewModel by activityViewModels()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        recyclerView = binding.rvPokemonList
        layoutManager = LinearLayoutManager(requireContext())
        searchView = binding.searchViewRoot

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = layoutManager
        adapter = PokemonListAdapter(requireContext(),this)
        recyclerView.adapter = adapter

        val searchText = viewModel.getSearchQuery()
        val filteredList = viewModel.getFilteredList()
        searchView.setText(searchText)
        adapter.updateList(filteredList as ArrayList<CustomPokemonList>)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                Log.d(
                    "TAG",
                    "onScrolled: $visibleItemCount $totalItemCount $firstVisibleItemPosition"
                )

                if (!viewModel.isLoading && ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount || visibleItemCount > totalItemCount)
                    && firstVisibleItemPosition >= 0) {
                    Log.d("TAG", "onViewCreated: fetchPokemon(adapter) recycler ")
                    viewModel.fetchFewPokemonList()
                }
            }
        })



        viewModel.getCustomPokemonList().observe(viewLifecycleOwner) {
            Log.d("TAG", "onViewCreated: observer called  getCustomPokemonList() ")
//            Log.d("TAG", "onViewCreated: customPokemonList.size = ${it.size} ")
            if (!viewModel.isSearching) {
                adapter.updatePokemonList(it)
            } else {
                val searchText = searchView.text.toString()
                val filteredList = viewModel.searchTask(searchText)
                adapter.updateList(filteredList as ArrayList<CustomPokemonList>)
            }
        }



        searchView.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Call the searchTask function when the user types something in the EditText view
                Log.d("TAG", "onTextChanged: $s")


                val searchText = searchView.text.toString()
                val filteredList = viewModel.searchTask(searchText)

                // Store the search query and filtered list in the ViewModel
                viewModel.setSearchQuery(searchText)
                viewModel.setFilteredList(filteredList!!)


                if (viewModel.isSearching || viewModel.isLoading) {
                    adapter.updateList(filteredList as ArrayList<CustomPokemonList>)
                }

//                val visibleItemCount = layoutManager.childCount
//                if(visibleItemCount > filteredList.size ){
//                    viewModel.fetchFewPokemonList()
//                }

                viewModel.isSearching = viewModel.getSearchQuery().isNotEmpty()
                Log.d("TAG", "onTextChanged: is Searching : ${viewModel.isSearching}")

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {}
        })

    }

    override fun onPokemonClick(id: Int) {
        viewModel.setId(id)
        findNavController().navigate(R.id.action_pokemonListFragment_to_pokemonInfoFragment)

    }


}