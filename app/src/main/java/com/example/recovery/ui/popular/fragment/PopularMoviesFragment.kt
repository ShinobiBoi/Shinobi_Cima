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
import com.example.recovery.databinding.FragmentPopularMoviesBinding
import com.example.recovery.ui.popular.repo.PopularRepo
import com.example.recovery.ui.popular.viewmodel.PopularViewModel
import com.example.recovery.ui.popular.viewmodel.PopularViewModelFactory
import com.example.recovery.ui.utilites.Connection
import com.example.recovery.ui.utilites.GridAdapter


class PopularMoviesFragment : Fragment(){

    private lateinit var popularAdapter: GridAdapter
    private lateinit var popularViewModel: PopularViewModel



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
        val binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)


        // first case:  there is internet
        if(Connection.isNetworkAvailable(requireContext())){

            //get movies
            binding.noInternetTextPopular.visibility=View.GONE
            popularViewModel.getPopularMovies()

            popularViewModel.currentPage.observe(viewLifecycleOwner){

                if (it==1){
                    binding.nextButtonPopular.visibility=View.VISIBLE
                    binding.previousButtonPopular.visibility=View.GONE
                }
                else if (it==20){
                    binding.nextButtonPopular.visibility=View.GONE
                    binding.previousButtonPopular.visibility=View.VISIBLE
                }
                else{
                    binding.nextButtonPopular.visibility=View.VISIBLE
                    binding.previousButtonPopular.visibility=View.VISIBLE
                }
            }
        }
        // second case:  there is no internet
        else{
            binding.noInternetTextPopular.visibility=View.VISIBLE
            //hide the rest
            binding.popularMoviesContent.visibility=View.GONE
        }



        popularAdapter = GridAdapter{
            onMovieClick(it)
        }
        popularViewModel.popularMovies.observe(viewLifecycleOwner){
            if (it != null) {
                popularAdapter.submitList(it)
            }
        }
        binding.mainPopularMovies.adapter = popularAdapter


        binding.nextButtonPopular.setOnClickListener(){
            popularViewModel.nextPage()
        }
        binding.previousButtonPopular.setOnClickListener(){
            popularViewModel.previousPage()
        }


        return binding.root
    }

    fun onMovieClick(movie:Movie) {
        val action=PopularMoviesFragmentDirections.actionPopularMoviesFragmentToDetailsFragment(
            FavMovie(movie.backdrop_path,movie.poster_path,movie.original_title,movie.original_language,movie.runtime
                ,movie.vote_average,movie.overview,movie.release_date,movie.id)
        )
        findNavController().navigate(action)
    }


}