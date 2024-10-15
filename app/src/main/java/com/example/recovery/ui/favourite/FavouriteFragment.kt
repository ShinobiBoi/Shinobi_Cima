package com.example.recovery.ui.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recovery.R
import com.example.recovery.data.local.LocalDs
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.ui.favourite.adapter.FavouriteAdapter
import com.example.recovery.ui.favourite.repo.FavouriteRepo
import com.example.recovery.ui.favourite.viewmodel.FavouriteViewModel
import com.example.recovery.ui.favourite.viewmodel.FavouriteViewModelFactory
import com.example.recovery.ui.popular.viewmodel.PopularViewModel
import com.example.recovery.utilites.ClickHandler
import com.example.recovery.utilites.GridAdapter


class FavouriteFragment : Fragment(),ClickHandler {

    private lateinit var noInternetText:TextView

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavouriteAdapter
    private var movies = ArrayList<FavMovie>()

    private lateinit var favouriteViewModel: FavouriteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = FavouriteViewModelFactory(FavouriteRepo(LocalDs(requireContext())))
        favouriteViewModel=ViewModelProvider(this,factory).get(FavouriteViewModel::class.java)



    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //get the fav movies
        favouriteViewModel.getFavMovie()

        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_favourite, container, false)


        //initialzie the views
        recyclerView=view.findViewById(R.id.favourite_movies)
        noInternetText = view.findViewById(R.id.empty_watchlist)

        favouriteViewModel.favMovies.observe(viewLifecycleOwner){


            // noInternetText visibility
            if(it.isEmpty())
                noInternetText.visibility=View.VISIBLE
            else
                noInternetText.visibility= View.GONE


            // update the list for the adapter
            if (it != null){
                movies.clear()
                movies.addAll(it)

            }


            adapter.notifyDataSetChanged()
        }

        adapter=FavouriteAdapter(movies,this)
        recyclerView.layoutManager=GridLayoutManager(context,2)
        recyclerView.adapter=adapter



        return view
    }

    override fun onMovieClick(movie:Movie) {
        val action= FavouriteFragmentDirections.actionFavouriteFragmentToDetailsFragment(
            FavMovie(movie.backdrop_path,movie.poster_path,movie.original_title,movie.original_language,movie.runtime
            ,movie.vote_average,movie.overview,movie.release_date,movie.id)
        )
        findNavController().navigate(action)
    }


}