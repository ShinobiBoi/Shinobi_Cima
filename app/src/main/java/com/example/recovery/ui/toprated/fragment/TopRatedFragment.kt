package com.example.recovery.ui.toprated.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recovery.R
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.data.remote.ApiClient
import com.example.recovery.databinding.FragmentTopRatedBinding
import com.example.recovery.ui.toprated.repo.TopRatedRepo
import com.example.recovery.ui.toprated.viewmodel.TopRatedViewModel
import com.example.recovery.ui.utilites.Connection
import com.example.recovery.ui.utilites.GridAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedFragment : Fragment() {


    private val topRatedViewModel: TopRatedViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentTopRatedBinding.inflate(inflater, container, false)

        lateinit var topRatedAdapter: GridAdapter

        // first case:  there is internet
        if (Connection.isNetworkAvailable(requireContext())) {

            //get movies
            topRatedViewModel.getTopRatedMovies()
            binding.noInternetTextToprated.visibility = View.GONE

            topRatedViewModel.currentPage.observe(viewLifecycleOwner) {

                if (it == 1) {
                    binding.nextButtonTop.visibility = View.VISIBLE
                    binding.previousButtonTop.visibility = View.GONE
                } else if (it == 20) {
                    binding.nextButtonTop.visibility = View.GONE
                    binding.previousButtonTop.visibility = View.VISIBLE
                } else {
                    binding.nextButtonTop.visibility = View.VISIBLE
                    binding.previousButtonTop.visibility = View.VISIBLE
                }

            }

        }
        // second case:  there is no internet
        else {
            binding.noInternetTextToprated.visibility = View.VISIBLE
            //hide the rest
            binding.topRatedMoviesContent.visibility = View.GONE
        }
        topRatedViewModel.topRatedMovies.observe(viewLifecycleOwner) {
            if (it != null) {
                topRatedAdapter.submitList(it)
            }
        }
        binding.nextButtonTop.setOnClickListener() {
            topRatedViewModel.nextPage()
        }
        binding.previousButtonTop.setOnClickListener() {
            topRatedViewModel.previousPage()
        }
        topRatedAdapter = GridAdapter() {
            onMovieClick(it)
        }
        binding.mainTopratedMovies.adapter = topRatedAdapter
        return binding.root
    }


    fun onMovieClick(movie: Movie) {
        val action = TopRatedFragmentDirections.actionTopRatedFragmentToDetailsFragment(
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