package com.example.recovery.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recovery.R
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.data.remote.ApiClient
import com.example.recovery.ui.home.repo.HomeRepo
import com.example.recovery.ui.home.viewmodel.HomeViewFactory
import com.example.recovery.ui.home.viewmodel.HomeViewModel
import com.example.recovery.utilites.ClickHandler
import com.example.recovery.utilites.Connection
import com.example.recovery.utilites.MyAdapter
import com.example.recovery.utilites.NowPlayingAdapter


class HomeFragment : Fragment(),ClickHandler {

    private lateinit var recyclerViewPopular: RecyclerView
    private lateinit var recyclerViewTopRated: RecyclerView
    private lateinit var recyclerViewNowPlaying: RecyclerView
    private lateinit var recyclerViewUpComing: RecyclerView

    private var popularMovies=ArrayList<Movie>()
    private var topRatedMovies=ArrayList<Movie>()
    private var upComingMovies=ArrayList<Movie>()


    private lateinit var popularAdapter: MyAdapter
    private lateinit var topRatedAdapter: MyAdapter
    private lateinit var upComingAdapter: MyAdapter
    private lateinit var nowPlayingAdapter: NowPlayingAdapter

    private lateinit var homeViewModel: HomeViewModel

    private var fetchData = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val homeViewModelFactory = HomeViewFactory(HomeRepo(ApiClient))
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)


        if(Connection.isNetworkAvailable(requireContext())){

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
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerViewPopular = view.findViewById(R.id.popularmovies)
        recyclerViewTopRated=view.findViewById(R.id.topratedmovies)
        recyclerViewNowPlaying=view.findViewById(R.id.now_playing_movie)
        recyclerViewUpComing=view.findViewById(R.id.upcoming_rv)


        val noInternetText = view.findViewById<TextView>(R.id.no_internet_text)
        val upcomingText = view.findViewById<TextView>(R.id.upcoming_text)
        val popularText = view.findViewById<TextView>(R.id.popular_text)
        val topRatedText = view.findViewById<TextView>(R.id.toprated_text)


        // first case: no fetched data and there is internet -> fetch & initialize normally
        if(!fetchData && Connection.isNetworkAvailable(requireContext())) {
            homeViewModel.getPopularMovies()
            homeViewModel.getTopRatedMovies()
            homeViewModel.getUpcomingMovies()
            homeViewModel.getNowPlayingMovies()
            fetchData = true
        }
        // second case: fetched data and there is internet -> don't show no internet text and initialize normally (like local data fetched)
        else if(Connection.isNetworkAvailable(requireContext())) {
            noInternetText.visibility = View.GONE
            // show titles
            upcomingText.visibility = View.VISIBLE
            popularText.visibility = View.VISIBLE
            topRatedText.visibility = View.VISIBLE

        }
        // third case: no fetched data and no internet -> show no internet text and hide titles
        else if(!fetchData && !Connection.isNetworkAvailable(requireContext())) {
            noInternetText.visibility = View.VISIBLE
            // hide rest of the titles
            upcomingText.visibility = View.GONE
            popularText.visibility = View.GONE
            topRatedText.visibility = View.GONE
        }







        initializeRecycleVies()


            return view
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle the back press in Fragment
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val builder= AlertDialog.Builder(requireContext())
                builder.setTitle("Confirmation")
                builder.setMessage("Are you sure u want to exit?")
                builder.setPositiveButton("Yes"){_, _->
                    requireActivity().finishAffinity()
                }
                builder.setNegativeButton("No"){_,_->
                }
                builder.create()
                builder.show()

            }
        })
    }

    private fun initializeRecycleVies(){

        // popular movies
        homeViewModel.popularMovies.observe(viewLifecycleOwner) {
            if (it != null) {
                popularMovies.clear()
                popularMovies.addAll(it)
            }
            popularAdapter.notifyDataSetChanged()
        }
        popularAdapter = MyAdapter(popularMovies,this)
        recyclerViewPopular.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewPopular.adapter = popularAdapter


        // top rated movies
        homeViewModel.topRatedMovies.observe(viewLifecycleOwner) {
            if (it != null) {
                topRatedMovies.clear()
                topRatedMovies.addAll(it)
            }
            topRatedAdapter.notifyDataSetChanged()
        }
        topRatedAdapter = MyAdapter(topRatedMovies,this)
        recyclerViewTopRated.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewTopRated.adapter = topRatedAdapter


        // upcoming movies
        homeViewModel.upcomingMovies.observe(viewLifecycleOwner){
            if (it != null){
                upComingMovies.clear()
                upComingMovies.addAll(it)
            }
            upComingAdapter.notifyDataSetChanged()

        }
        upComingAdapter= MyAdapter(upComingMovies,this)
        recyclerViewUpComing.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        recyclerViewUpComing.adapter=upComingAdapter

        // now playing movie
        homeViewModel.nowPlayingMovies.observe(viewLifecycleOwner) {
            if (it != null) {
                nowPlayingAdapter = NowPlayingAdapter(it)
                recyclerViewNowPlaying.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                recyclerViewNowPlaying.adapter=nowPlayingAdapter

            }
        }

    }

    override fun onMovieClick(movie:Movie) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
            FavMovie(movie.backdrop_path,movie.poster_path,movie.original_title,movie.original_language,movie.runtime
                ,movie.vote_average,movie.overview,movie.release_date,movie.id)
        )
        findNavController().navigate(action)
    }

}

