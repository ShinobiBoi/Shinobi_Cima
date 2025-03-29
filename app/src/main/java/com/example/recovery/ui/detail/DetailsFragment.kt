package com.example.recovery.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.recovery.R
import com.example.recovery.data.local.LocalDs
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.data.remote.ApiClient
import com.example.recovery.databinding.FragmentDetailsBinding
import com.example.recovery.ui.detail.repo.DetailRepo
import com.example.recovery.ui.detail.viewmodel.DetailViewModel
import com.example.recovery.ui.utilites.CastAdapter
import com.example.recovery.ui.utilites.Connection
import com.example.recovery.ui.utilites.SimilarAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(){


   private val detailViewModel : DetailViewModel by viewModels()

   private lateinit var currMovie:FavMovie

    private lateinit var adapter: SimilarAdapter
    private lateinit var castAdapter: CastAdapter


    private var favourite= false

    private val args :DetailsFragmentArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentDetailsBinding>(inflater,R.layout.fragment_details,container,false)
        // Inflate the layout for this fragment



        // initialize
        initializeViews(binding)



        //if there is network get the movie with details from the api
        // if not the movie is saved with its details in the room database
        if (Connection.isNetworkAvailable(requireContext())){



            binding.textCast.visibility=View.VISIBLE
            binding.textSimilar.visibility=View.VISIBLE

            detailViewModel.getDetailMovie(args.favmovie.id.toString())

            detailViewModel.detailMovie.observe(viewLifecycleOwner){

                // save the detailmovie into favmovie
                 currMovie=FavMovie(it.backdrop_path,it.poster_path,it.original_title,it.original_language,
                    it.runtime,it.vote_average,it.overview,it.release_date,it.id)


                //get cast and similar movies of the curr movie
                getSimilarMovies(currMovie.id.toString())

                getMovieCast(currMovie.id.toString())




                // set the fav button
                binding.favbtn.setImageResource(R.drawable.baseline_bookmark_border_24)

                detailViewModel.favMovies.observe(viewLifecycleOwner){ it2->


                    isFavourite(binding,it2,currMovie)


                }

                // bind the data into the vies
                binding.favMovie=currMovie


                binding.favbtn.setOnClickListener{
                    if (favourite){

                        deleteMovie(binding,currMovie)

                    }else{

                        insertMovie(binding,currMovie)

                    }
                }


            }


        }else{



            binding.textCast.visibility=View.GONE
            binding.textSimilar.visibility=View.GONE


            currMovie=args.favmovie


            // set the fav button
            binding.favbtn.setImageResource(R.drawable.baseline_bookmark_border_24)

            detailViewModel.favMovies.observe(viewLifecycleOwner){

                isFavourite(binding,it, currMovie)


            }

            // bind the data into the vies
            binding.favMovie=currMovie

            binding.favbtn.setOnClickListener{
                if (favourite){

                    deleteMovie(binding,currMovie)

                }else{

                    insertMovie(binding,currMovie)

                }
            }



        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (::currMovie.isInitialized){
            (activity as? AppCompatActivity)?.supportActionBar?.title = currMovie.original_title

        }
    }

    private fun insertMovie(binding: FragmentDetailsBinding,currMovie: FavMovie) {
        binding.favbtn.setImageResource(R.drawable.baseline_bookmark_24)
        detailViewModel.insertFavMovie(FavMovie(currMovie.backdrop_path,currMovie.poster_path,currMovie.original_title,currMovie.original_language,currMovie.runtime
            ,currMovie.vote_average,currMovie.overview,currMovie.release_date,currMovie.id))
        favourite=true

        Toast.makeText(requireContext(), "Added ${currMovie.original_title} to the watchlist", Toast.LENGTH_SHORT).show()


    }

    private fun deleteMovie(binding: FragmentDetailsBinding,currMovie: FavMovie) {

        binding.favbtn.setImageResource(R.drawable.baseline_bookmark_border_24)
        detailViewModel.deleteFavMovie(FavMovie(currMovie.backdrop_path,currMovie.poster_path,currMovie.original_title,currMovie.original_language,currMovie.runtime
            ,currMovie.vote_average,currMovie.overview,currMovie.release_date,currMovie.id))
        favourite=false
        Toast.makeText(requireContext(), "Removed ${currMovie.original_title} from the watchlist", Toast.LENGTH_SHORT).show()


    }

    private fun isFavourite(binding: FragmentDetailsBinding,it2: List<FavMovie>,favMovie:FavMovie) {


        if(it2.contains(favMovie)){
            binding.favbtn.setImageResource(R.drawable.baseline_bookmark_24)
            favourite=true
        }
        (activity as? AppCompatActivity)?.supportActionBar?.title = currMovie.original_title


    }



    @SuppressLint("NotifyDataSetChanged")
    private fun getMovieCast(id:String) {
        detailViewModel.getMovieCast(id)

        detailViewModel.movieCast.observe(viewLifecycleOwner){

            if (it !=null){
                castAdapter.submitList(it)
            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getSimilarMovies(id:String) {

        detailViewModel.getSimilarMovies(id)

        detailViewModel.similarMovies.observe(viewLifecycleOwner){
            if (it!= null){
                adapter.submitList(it)
            }

        }

    }

    private fun initializeViews(binding: FragmentDetailsBinding) {


        adapter = SimilarAdapter{
            onSimilarMovieClick(it)
        }
        binding.similarMovies.adapter = adapter


        castAdapter= CastAdapter()
        binding.movieCast.adapter = castAdapter
    }


   fun onSimilarMovieClick(movie: Movie) {

        val action = DetailsFragmentDirections.actionDetailsFragmentSelf(
            FavMovie(movie.backdrop_path,movie.poster_path,movie.original_title,movie.original_language,movie.runtime
                ,movie.vote_average,movie.overview,movie.release_date,movie.id)
        )
        findNavController().navigate(action)

    }


}