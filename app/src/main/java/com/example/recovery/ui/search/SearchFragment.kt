package com.example.recovery.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recovery.R
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.data.remote.ApiClient
import com.example.recovery.databinding.FragmentSearchBinding
import com.example.recovery.ui.search.repo.SearchRepo
import com.example.recovery.ui.search.viewmodel.SearchViewModel
import com.example.recovery.ui.search.viewmodel.SearchViewModelFactory
import com.example.recovery.ui.utilites.Connection
import com.example.recovery.ui.utilites.GridAdapter


class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var gridAdapter: GridAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val searchViewModelFactory = SearchViewModelFactory(SearchRepo(ApiClient))
        searchViewModel =
            ViewModelProvider(this, searchViewModelFactory).get(SearchViewModel::class.java)

    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSearchBinding.inflate(inflater, container, false)


        if (Connection.isNetworkAvailable(requireContext())) {

            // if there is network get popular movies to show
            searchViewModel.getPopularMovies()
            binding.searchView.visibility = View.VISIBLE
            binding.emptyResult.text = "No results found."
        } else {
            // set the text to no internet and hide the recycle view
            binding.searchContent.visibility = View.GONE
            binding.emptyResult.text = "No internet connection, please try again later."
            binding.emptyResult.visibility = View.VISIBLE
        }



        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty())
                    searchViewModel.getMovies(newText)
                else
                    searchViewModel.getPopularMovies()
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchViewModel.getMovies(query)
                }
                binding.searchView.clearFocus()
                return true
            }

        })




        searchViewModel.movies.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                gridAdapter.submitList(it)
                // remove empty results text
                binding.searchRv.visibility = View.VISIBLE
                binding.emptyResult.visibility = View.GONE
            } else {
                gridAdapter.submitList(emptyList())
                // show empty results text
                binding.searchRv.visibility = View.GONE
                binding.emptyResult.visibility = View.VISIBLE
            }
        }

        gridAdapter = GridAdapter() {
            onMovieClick(it)
        }
        binding.searchRv.adapter = gridAdapter

        return binding.root
    }


    fun onMovieClick(movie: Movie) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(
            FavMovie(
                movie.backdrop_path,
                movie.poster_path,
                movie.original_title,
                movie.original_language,
                movie.runtime,
                movie.vote_average,
                movie.overview,
                movie.release_date,
                movie.id
            )
        )
        findNavController().navigate(action)
    }

}