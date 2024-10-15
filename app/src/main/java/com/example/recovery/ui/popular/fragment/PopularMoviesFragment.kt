package com.example.recovery.ui.popular.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recovery.R
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.data.remote.ApiClient
import com.example.recovery.ui.popular.repo.PopularRepo
import com.example.recovery.ui.popular.viewmodel.PopularViewModel
import com.example.recovery.ui.popular.viewmodel.PopularViewModelFactory
import com.example.recovery.utilites.ClickHandler
import com.example.recovery.utilites.Connection
import com.example.recovery.utilites.GridAdapter


class PopularMoviesFragment : Fragment() ,ClickHandler{

    private lateinit var recyclerViewPopular: RecyclerView
    private lateinit var popularAdapter: GridAdapter
    private var movies = ArrayList<Movie>()

    private lateinit var popularViewModel: PopularViewModel


    private lateinit var noInternetText :TextView
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val popularFactory =PopularViewModelFactory(PopularRepo(ApiClient))
        popularViewModel=ViewModelProvider(this,popularFactory).get(PopularViewModel::class.java)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_popular_movies, container, false)


        //initialize views
        initialize(view)


        // first case:  there is internet
        if(Connection.isNetworkAvailable(requireContext())){

            //get movies
            noInternetText.visibility=View.GONE
            popularViewModel.getPopularMovies()

            popularViewModel.currentPage.observe(viewLifecycleOwner){

                if (it==1){
                    nextButton.visibility=View.VISIBLE
                    previousButton.visibility=View.GONE
                }
                else if (it==20){
                    nextButton.visibility=View.GONE
                    previousButton.visibility=View.VISIBLE
                }
                else{
                    nextButton.visibility=View.VISIBLE
                    previousButton.visibility=View.VISIBLE
                }
            }
        }
        // second case:  there is no internet
        else{
            noInternetText.visibility=View.VISIBLE
            //hide the rest
            nextButton.visibility=View.GONE
            previousButton.visibility=View.GONE
        }




        popularViewModel.popularMovies.observe(viewLifecycleOwner){

            if (it != null) {

                movies.clear()
                movies.addAll(it)


            }
            popularAdapter.notifyDataSetChanged()

        }

        popularAdapter = GridAdapter(movies,this)
        recyclerViewPopular.layoutManager = GridLayoutManager(context,2)
        recyclerViewPopular.adapter = popularAdapter


        nextButton.setOnClickListener(){

            popularViewModel.nextPage()

        }
        previousButton.setOnClickListener(){
            popularViewModel.previousPage()
        }


        return view
    }

    private fun initialize(view: View) {

        recyclerViewPopular=view.findViewById(R.id.main_popular_movies)
        noInternetText = view.findViewById(R.id.no_internet_text_popular)
        nextButton=view.findViewById(R.id.next_button_popular)
        previousButton=view.findViewById(R.id.previous_button_popular)

    }

    override fun onMovieClick(movie:Movie) {
        val action=PopularMoviesFragmentDirections.actionPopularMoviesFragmentToDetailsFragment(
            FavMovie(movie.backdrop_path,movie.poster_path,movie.original_title,movie.original_language,movie.runtime
                ,movie.vote_average,movie.overview,movie.release_date,movie.id)
        )
        findNavController().navigate(action)
    }


}