package com.example.recovery.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.data.remote.ApiClient
import com.example.recovery.databinding.FragmentHomeBinding
import com.example.recovery.ui.home.repo.HomeRepo
import com.example.recovery.ui.home.viewmodel.HomeViewModel
import com.example.recovery.ui.utilites.Connection
import com.example.recovery.ui.utilites.MyAdapter
import com.example.recovery.ui.utilites.NowPlayingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {


    private lateinit var popularAdapter: MyAdapter
    private lateinit var topRatedAdapter: MyAdapter
    private lateinit var upComingAdapter: MyAdapter
    private lateinit var nowPlayingAdapter: NowPlayingAdapter

    private  val homeViewModel: HomeViewModel by viewModels()

    private var fetchData = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Connection.isNetworkAvailable(requireContext())) {

            homeViewModel.getPopularMovies()
            homeViewModel.getTopRatedMovies()
            homeViewModel.getUpcomingMovies()
            homeViewModel.getNowPlayingMovies()

            fetchData = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentHomeBinding.inflate(inflater, container, false)



        // first case: no fetched data and there is internet -> fetch & initialize normally
        if (!fetchData && Connection.isNetworkAvailable(requireContext())) {
            homeViewModel.getPopularMovies()
            homeViewModel.getTopRatedMovies()
            homeViewModel.getUpcomingMovies()
            homeViewModel.getNowPlayingMovies()
            fetchData = true
        }
        // second case: fetched data and there is internet -> don't show no internet text and initialize normally (like local data fetched)
        else if (Connection.isNetworkAvailable(requireContext())) {
            binding.noInternetText.visibility = View.GONE
            // show titles
            binding.scrollHome.visibility = View.VISIBLE

        }
        // third case: no fetched data and no internet -> show no internet text and hide titles
        else if (!fetchData && !Connection.isNetworkAvailable(requireContext())) {
            binding.noInternetText.visibility = View.VISIBLE
            // hide rest of the titles
            binding.scrollHome.visibility = View.GONE
        }
        initializeRecycleVies(binding)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Confirmation")
                    builder.setMessage("Are you sure u want to exit?")
                    builder.setPositiveButton("Yes") { _, _ ->
                        requireActivity().finishAffinity()
                    }
                    builder.setNegativeButton("No") { _, _ ->
                    }
                    builder.create()
                    builder.show()

                }
            })
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle the back press in Fragment

    }

    private fun initializeRecycleVies(binding: FragmentHomeBinding) {

        // popular movies
        popularAdapter = MyAdapter {
            onMovieClick(it)
        }
        binding.popularmovies.adapter = popularAdapter
        homeViewModel.popularMovies.observe(viewLifecycleOwner) {
            if (it != null) {
                popularAdapter.submitList(it)
            }
        }
        // top rated movies
        topRatedAdapter = MyAdapter() {
            onMovieClick(it)
        }
        binding.topratedmovies.adapter = topRatedAdapter
        homeViewModel.topRatedMovies.observe(viewLifecycleOwner) {
            if (it != null) {
                topRatedAdapter.submitList(it)
            }
        }
        // upcoming movies
        upComingAdapter = MyAdapter() {
            onMovieClick(it)
        }
        binding.upcomingRv.adapter = upComingAdapter
        homeViewModel.upcomingMovies.observe(viewLifecycleOwner) {
            if (it != null) {
                upComingAdapter.submitList(it)
            }
        }

        // now playing movie
        nowPlayingAdapter = NowPlayingAdapter()
        binding.nowPlayingMovie.adapter = nowPlayingAdapter
        homeViewModel.nowPlayingMovies.observe(viewLifecycleOwner) {
            if (it != null) {
                nowPlayingAdapter.submitList(it)
            }
        }
    }

    fun onMovieClick(movie: Movie) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
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

