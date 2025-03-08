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
import com.example.recovery.databinding.FragmentFavouriteBinding
import com.example.recovery.ui.favourite.adapter.FavouriteAdapter
import com.example.recovery.ui.favourite.repo.FavouriteRepo
import com.example.recovery.ui.favourite.viewmodel.FavouriteViewModel
import com.example.recovery.ui.favourite.viewmodel.FavouriteViewModelFactory
import com.example.recovery.ui.popular.viewmodel.PopularViewModel


class FavouriteFragment : Fragment() {

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
        // Inflate the layout for this fragment
        val binding= FragmentFavouriteBinding.inflate(inflater,container,false)

       val adapter=FavouriteAdapter{
            onMovieClick(it)
        }

        favouriteViewModel.favMovies.observe(viewLifecycleOwner){


            // noInternetText visibility
            if(it.isEmpty())
                binding.emptyWatchlist.visibility=View.VISIBLE
            else
                binding.emptyWatchlist.visibility= View.GONE


            // update the list for the adapter
            if (it != null){
                adapter.submitList(it)
            }
        }

        binding.favouriteMovies.adapter=adapter
        return binding.root
    }

     fun onMovieClick(movie:Movie) {
        val action= FavouriteFragmentDirections.actionFavouriteFragmentToDetailsFragment(
            FavMovie(movie.backdrop_path,movie.poster_path,movie.original_title,movie.original_language,movie.runtime
            ,movie.vote_average,movie.overview,movie.release_date,movie.id)
        )
        findNavController().navigate(action)
    }


}