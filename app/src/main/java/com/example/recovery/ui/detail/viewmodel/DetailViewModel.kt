package com.example.recovery.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recovery.data.model.cast.Cast
import com.example.recovery.data.model.cast.toCast
import com.example.recovery.data.model.detailmovie.DetailMovie
import com.example.recovery.data.model.detailmovie.toDetailMovie
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.data.model.movie.toMovie
import com.example.recovery.domain.repository.DetailRepoInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repo: DetailRepoInterface) :ViewModel(){

    private val _similarMovies= MutableLiveData<List<Movie>>()
    val similarMovies: LiveData<List<Movie>> =_similarMovies

    private val _movieCast= MutableLiveData<List<Cast>>()
    val movieCast: LiveData<List<Cast>> =_movieCast

    private val _detailMovie= MutableLiveData<DetailMovie>()
    val detailMovie: LiveData<DetailMovie> =_detailMovie


    val favMovies: LiveData<List<FavMovie>> = repo.getFavMovies()


    fun getSimilarMovies(id:String){
        viewModelScope.launch {
            val moviesResponse = repo.getSimilarMovies(id,1)
            val movies = moviesResponse.results.map { it.toMovie() }
            _similarMovies.postValue(movies)
        }
    }

    fun getMovieCast(id:String){
        viewModelScope.launch {
            val castResponse=repo.getMovieCast(id,1)
            val cast = castResponse.cast.map { it.toCast() }
            _movieCast.postValue(cast)
        }
    }

    fun getDetailMovie(id:String){
        viewModelScope.launch {
            val movie = repo.getDetailMovie(id,1)
            _detailMovie.postValue(movie.toDetailMovie())
        }
    }

    fun insertFavMovie(favMovie: FavMovie){
        viewModelScope.launch {
            repo.insertFavMovie(favMovie)
        }
    }

    fun deleteFavMovie(favMovie: FavMovie){
        viewModelScope.launch {
            repo.deleteFavMovie(favMovie)
        }
    }



}