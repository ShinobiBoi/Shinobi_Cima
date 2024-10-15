package com.example.recovery.ui.detail

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recovery.R
import com.example.recovery.data.local.LocalDs
import com.example.recovery.data.model.cast.Cast
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.data.remote.ApiClient
import com.example.recovery.ui.detail.repo.DetailRepo
import com.example.recovery.ui.detail.viewmodel.DetailViewModel
import com.example.recovery.ui.detail.viewmodel.DetailViewModelFactory
import com.example.recovery.utilites.CastAdapter
import com.example.recovery.utilites.Connection
import com.example.recovery.utilites.SimilarAdapter
import com.example.recovery.utilites.SimilarClickHandler


class DetailsFragment : Fragment() ,SimilarClickHandler{


   private lateinit var movieImage: ImageView
   private lateinit var moviePoster:ImageView

   private lateinit var movieName: TextView
   private lateinit var movieLanguage: TextView
   private lateinit var movieRating: TextView
   private lateinit var movieDetail: TextView
   private lateinit var movieDuration:TextView
   private lateinit var movieRelease:TextView

   private lateinit var favBtn : ImageButton

   private lateinit var detailViewModel: DetailViewModel

   private lateinit var currMovie:FavMovie


    private lateinit var similarMovies:RecyclerView
    private var movies = ArrayList<Movie>()
    private lateinit var adapter:SimilarAdapter


    private lateinit var movieCast:RecyclerView
    private var casts = ArrayList<Cast>()
    private lateinit var castAdapter: CastAdapter


    private var favourite= false




    private val args :DetailsFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val factory =DetailViewModelFactory(DetailRepo(ApiClient,LocalDs(requireContext())))
        detailViewModel= ViewModelProvider(this,factory).get(DetailViewModel::class.java)

        detailViewModel.getFavMovies()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)


        // initialize
        initializeViews(view)



        //if there is network get the movie with details from the api
        // if not the movie is saved with its details in the room database
        if (Connection.isNetworkAvailable(requireContext())){

            detailViewModel.getDetailMovie(args.favmovie.id.toString())

            detailViewModel.detailMovie.observe(viewLifecycleOwner){

                // save the detailmovie into favmovie
                 currMovie=FavMovie(it.backdrop_path,it.poster_path,it.original_title,it.original_language,
                    it.runtime,it.vote_average,it.overview,it.release_date,it.id)


                //get cast and similar movies of the curr movie
                getSimilarMovies(currMovie.id.toString())

                getMovieCast(currMovie.id.toString())




                // set the fav button
                favBtn.setImageResource(R.drawable.baseline_bookmark_border_24)

                detailViewModel.favMovies.observe(viewLifecycleOwner){ it2->


                    isFavourite(it2,currMovie)


                }

                // bind the data into the vies
                bindViews(currMovie)


                favBtn.setOnClickListener(){
                    if (favourite){

                        deleteMovie(currMovie)

                    }else{

                        insertMovie(currMovie)

                    }
                }
                (activity as? AppCompatActivity)?.supportActionBar?.title = currMovie.original_title


            }


        }else{
            currMovie=args.favmovie


            // set the fav button
            favBtn.setImageResource(R.drawable.baseline_bookmark_border_24)

            detailViewModel.favMovies.observe(viewLifecycleOwner){

                isFavourite(it, currMovie)


            }

            // bind the data into the vies
            bindViews(currMovie)

            favBtn.setOnClickListener(){
                if (favourite){

                    deleteMovie(currMovie)

                }else{

                    insertMovie(currMovie)

                }
            }

            (activity as? AppCompatActivity)?.supportActionBar?.title = currMovie.original_title


        }

        return view
    }

    private fun insertMovie(currMovie: FavMovie) {
        favBtn.setImageResource(R.drawable.baseline_bookmark_24)
        detailViewModel.insertFavMovie(FavMovie(currMovie.backdrop_path,currMovie.poster_path,currMovie.original_title,currMovie.original_language,currMovie.runtime
            ,currMovie.vote_average,currMovie.overview,currMovie.release_date,currMovie.id))
        favourite=true

        Toast.makeText(requireContext(), "Added ${currMovie.original_title} to the watchlist", Toast.LENGTH_SHORT).show()


    }

    private fun deleteMovie(currMovie: FavMovie) {

        favBtn.setImageResource(R.drawable.baseline_bookmark_border_24)
        detailViewModel.deleteFavMovie(FavMovie(currMovie.backdrop_path,currMovie.poster_path,currMovie.original_title,currMovie.original_language,currMovie.runtime
            ,currMovie.vote_average,currMovie.overview,currMovie.release_date,currMovie.id))
        favourite=false
        Toast.makeText(requireContext(), "Removed ${currMovie.original_title} from the watchlist", Toast.LENGTH_SHORT).show()


    }

    private fun bindViews(favMovie: FavMovie) {

        Glide.with(movieImage).load("https://image.tmdb.org/t/p/w500"+favMovie.backdrop_path).into(movieImage)
        Glide.with(moviePoster).load("https://image.tmdb.org/t/p/w500"+favMovie.poster_path).into(moviePoster)

        movieName.text=favMovie.original_title
        movieLanguage.text="Language: ${favMovie.original_language}"

        movieDuration.text="Duration: ${favMovie.runtime}min"
        movieRelease.text="Release date: ${favMovie.release_date.split("-")[0]}"


        val ratingNumber = String.format("%.1f", favMovie.vote_average)
        val htmlText = "<font color='#FFFFFF'>Rating: </font>  <font color='#FFEB3B'>${ratingNumber}★</font>"
        movieRating.text= Html.fromHtml(htmlText)

        movieDetail.text="Overview:\n${favMovie.overview}"

    }

    private fun isFavourite(it2: List<FavMovie>,favMovie:FavMovie) {
        for (movie in it2){
            if (movie.id == favMovie.id){
                favBtn.setImageResource(R.drawable.baseline_bookmark_24)
                favourite=true
            }
        }

    }

    private fun getMovieCast(id:String) {
        detailViewModel.getMovieCast(id)

        detailViewModel.movieCast.observe(viewLifecycleOwner){

            if (it !=null){
                casts.clear()
                casts.addAll(it)
            }
            castAdapter.notifyDataSetChanged()
        }
    }

    private fun getSimilarMovies(id:String) {

        detailViewModel.getSimilarMovies(id)

        detailViewModel.similarMovies.observe(viewLifecycleOwner){
            if (it!= null){
                movies.clear()
                movies.addAll(it)
            }
            adapter.notifyDataSetChanged()
        }

    }

    private fun initializeViews(view: View) {

        movieImage=view.findViewById(R.id.detail_movie_image)
        movieName=view.findViewById(R.id.detail_movie_name)
        movieLanguage=view.findViewById(R.id.detail_language)
        movieRating=view.findViewById(R.id.detail_rating)
        movieDetail=view.findViewById(R.id.readmore)
        movieDuration=view.findViewById(R.id.detail_duration)
        movieRelease=view.findViewById(R.id.detail_release_date)
        similarMovies=view.findViewById(R.id.similar_movies)
        movieCast=view.findViewById(R.id.movie_cast)
        moviePoster=view.findViewById(R.id.detail_movie_poster)

        favBtn=view.findViewById(R.id.favbtn)

        adapter = SimilarAdapter(movies,this)
        similarMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        similarMovies.adapter = adapter


        castAdapter=CastAdapter(casts)
        movieCast.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        movieCast.adapter = castAdapter
    }


    override fun onSimilarMovieClick(id: Int) {
       detailViewModel.getDetailMovie(id.toString())
    }


}