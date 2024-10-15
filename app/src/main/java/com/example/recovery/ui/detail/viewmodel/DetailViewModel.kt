package com.example.recovery.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recovery.data.model.cast.Cast
import com.example.recovery.data.model.detailmovie.DetailMovie
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.ui.detail.repo.DetailRepoInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repo: DetailRepoInterface) :ViewModel(){

    private val _similarMovies= MutableLiveData<List<Movie>>()
    val similarMovies: LiveData<List<Movie>> =_similarMovies

    private val _movieCast= MutableLiveData<List<Cast>>()
    val movieCast: LiveData<List<Cast>> =_movieCast

    private val _detailMovie= MutableLiveData<DetailMovie>()
    val detailMovie: LiveData<DetailMovie> =_detailMovie

    private val _favMovies = MutableLiveData<List<FavMovie>>()
    val favMovies: LiveData<List<FavMovie>> = _favMovies


    fun getSimilarMovies(id:String){
        viewModelScope.launch {
            val movies = repo.getSimilarMovies(id,1)
            _similarMovies.postValue(movies.results)
        }
    }

    fun getMovieCast(id:String){
        viewModelScope.launch {
            val cast=repo.getMovieCast(id,1)
            _movieCast.postValue(cast.cast)
        }
    }

    fun getDetailMovie(id:String){
        viewModelScope.launch {
            val movie = repo.getDetailMovie(id,1)
            _detailMovie.postValue(movie)
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

    fun getFavMovies() {
        viewModelScope.launch(Dispatchers.Default){
            _favMovies.postValue(repo.getFavMovies())
        }
    }


}