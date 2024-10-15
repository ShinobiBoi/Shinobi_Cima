package com.example.recovery.ui.upcoming.fragment

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
import com.example.recovery.ui.upcoming.repo.UpComingRepo
import com.example.recovery.ui.upcoming.viewmodel.UpComingViewModel
import com.example.recovery.ui.upcoming.viewmodel.UpComingViewModelFactory
import com.example.recovery.utilites.ClickHandler
import com.example.recovery.utilites.Connection
import com.example.recovery.utilites.GridAdapter


class UpComingFragment : Fragment() ,ClickHandler{

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GridAdapter
    private  var movies = ArrayList<Movie>()

    private lateinit var upComingViewModel: UpComingViewModel

    private lateinit var noInternetText :TextView
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val upComingFactory = UpComingViewModelFactory(UpComingRepo(ApiClient))
        upComingViewModel=ViewModelProvider(this,upComingFactory).get(UpComingViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_up_coming, container, false)


        //initialize views
        initialize(view)



        // first case:  there is internet
        if(Connection.isNetworkAvailable(requireContext())) {

            //get movies
            upComingViewModel.getUpComingMovies()
            noInternetText.visibility = View.GONE

            upComingViewModel.currentPage.observe(viewLifecycleOwner){

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

            noInternetText.visibility = View.VISIBLE
            // hide the rest
            nextButton.visibility=View.GONE
            previousButton.visibility=View.GONE
        }




        upComingViewModel.upComingMovies.observe(viewLifecycleOwner){
            if (it!= null){
                movies.clear()
                movies.addAll(it)
            }
            adapter.notifyDataSetChanged()

        }




        nextButton.setOnClickListener(){
            upComingViewModel.nextPage()
        }
        previousButton.setOnClickListener(){
            upComingViewModel.previousPage()
        }

        return view
    }

    private fun initialize(view: View) {

        recyclerView=view.findViewById(R.id.main_upcoming_movies)
        noInternetText = view.findViewById(R.id.no_internet_text_upcoming)
        nextButton=view.findViewById(R.id.next_button_coming)
        previousButton=view.findViewById(R.id.previous_button_coming)

        adapter= GridAdapter(movies,this)
        recyclerView.layoutManager=GridLayoutManager(context,2)
        recyclerView.adapter=adapter

    }

    override fun onMovieClick(movie:Movie) {
        val action= UpComingFragmentDirections.actionUpComingFragmentToDetailsFragment(
            FavMovie(movie.backdrop_path,movie.poster_path,movie.original_title,movie.original_language,movie.runtime
                ,movie.vote_average,movie.overview,movie.release_date,movie.id)
        )
        findNavController().navigate(action)
    }


}