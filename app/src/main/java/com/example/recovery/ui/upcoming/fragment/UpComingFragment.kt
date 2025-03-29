package com.example.recovery.ui.upcoming.fragment

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
import com.example.recovery.databinding.FragmentUpComingBinding
import com.example.recovery.ui.upcoming.repo.UpComingRepo
import com.example.recovery.ui.upcoming.viewmodel.UpComingViewModel
import com.example.recovery.ui.utilites.Connection
import com.example.recovery.ui.utilites.GridAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpComingFragment : Fragment(){

    private lateinit var adapter: GridAdapter
    private  val upComingViewModel: UpComingViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentUpComingBinding.inflate(inflater, container, false)

        // first case:  there is internet
        if(Connection.isNetworkAvailable(requireContext())) {

            //get movies
            upComingViewModel.getUpComingMovies()
            binding.noInternetTextUpcoming.visibility = View.GONE

            upComingViewModel.currentPage.observe(viewLifecycleOwner){

                if (it==1){
                    binding.nextButtonComing.visibility=View.VISIBLE
                    binding.previousButtonComing.visibility=View.GONE
                }
                else if (it==20){
                    binding.nextButtonComing.visibility=View.GONE
                    binding.previousButtonComing.visibility=View.VISIBLE
                }
                else{
                    binding.nextButtonComing.visibility=View.VISIBLE
                    binding.previousButtonComing.visibility=View.VISIBLE
                }

            }

        }
        // second case:  there is no internet
        else{
            binding.noInternetTextUpcoming.visibility = View.VISIBLE
            // hide the rest
            binding.upComingMoviesContent.visibility=View.GONE
        }

        upComingViewModel.upComingMovies.observe(viewLifecycleOwner){
            if (it!= null){
                adapter.submitList(it)
            }
        }
        binding.nextButtonComing.setOnClickListener(){
            upComingViewModel.nextPage()
        }
        binding.previousButtonComing.setOnClickListener(){
            upComingViewModel.previousPage()
        }

        adapter= GridAdapter{
            onMovieClick(it)
        }
        binding.mainUpcomingMovies.adapter=adapter

        return binding.root
    }



     fun onMovieClick(movie:Movie) {
        val action= UpComingFragmentDirections.actionUpComingFragmentToDetailsFragment(
            FavMovie(movie.backdrop_path,movie.poster_path,movie.original_title,movie.original_language,movie.runtime
                ,movie.vote_average,movie.overview,movie.release_date,movie.id)
        )
        findNavController().navigate(action)
    }


}