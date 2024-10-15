package com.example.recovery.ui.toprated.fragment

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
import com.example.recovery.ui.toprated.repo.TopRatedRepo
import com.example.recovery.ui.toprated.viewmodel.TopRatedViewModel
import com.example.recovery.ui.toprated.viewmodel.TopRatedViewModelFactory
import com.example.recovery.utilites.ClickHandler
import com.example.recovery.utilites.Connection
import com.example.recovery.utilites.GridAdapter


class TopRatedFragment : Fragment(),ClickHandler {


    private lateinit var recyclerView: RecyclerView
    private lateinit var topRatedAdapter: GridAdapter
    private var movies = ArrayList<Movie>()

    private lateinit var topRatedViewModel: TopRatedViewModel

    private lateinit var nextButton: Button
    private lateinit var previousButton: Button

    private lateinit var noInternetText :TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val topRatedViewModelFactory =TopRatedViewModelFactory (TopRatedRepo(ApiClient))
        topRatedViewModel=ViewModelProvider(this,topRatedViewModelFactory).get(TopRatedViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_top_rated, container, false)

        //initialize views
        initialize(view)


        // first case:  there is internet
        if(Connection.isNetworkAvailable(requireContext())){

            //get movies
            topRatedViewModel.getTopRatedMovies()
            noInternetText.visibility=View.GONE

            topRatedViewModel.currentPage.observe(viewLifecycleOwner){

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


        topRatedViewModel.topRatedMovies.observe(viewLifecycleOwner){
            if (it != null){
                movies.clear()
                movies.addAll(it)
            }
            topRatedAdapter.notifyDataSetChanged()
        }




        nextButton.setOnClickListener(){
            topRatedViewModel.nextPage()
        }
        previousButton.setOnClickListener(){
            topRatedViewModel.previousPage()
        }

        return view
    }

    private fun initialize(view: View) {

        recyclerView=view.findViewById(R.id.main_toprated_movies)
        noInternetText = view.findViewById(R.id.no_internet_text_toprated)
        nextButton=view.findViewById(R.id.next_button_top)
        previousButton=view.findViewById(R.id.previous_button_top)

        recyclerView.layoutManager=GridLayoutManager(context,2)
        topRatedAdapter=GridAdapter(movies,this)
        recyclerView.adapter=topRatedAdapter

    }

    override fun onMovieClick(movie:Movie) {
        val action=TopRatedFragmentDirections.actionTopRatedFragmentToDetailsFragment(
            FavMovie(movie.backdrop_path,movie.poster_path,movie.original_title,movie.original_language,movie.runtime
                ,movie.vote_average,movie.overview,movie.release_date,movie.id)
        )
        findNavController().navigate(action)
    }


}