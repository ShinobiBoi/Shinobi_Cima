package com.example.recovery.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.data.model.movie.toMovie
import com.example.recovery.domain.repository.HomeRepoInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(
    private val repo: HomeRepoInterface) :ViewModel() {

    private val _popularMovies= MutableLiveData<List<Movie>>()
    val popularMovies:LiveData<List<Movie>> =_popularMovies


    private val _topRatedMovies=MutableLiveData<List<Movie>>()
    val topRatedMovies:LiveData<List<Movie>> =_topRatedMovies


    private val _upcomingMovies=MutableLiveData<List<Movie>>()
    val upcomingMovies:LiveData<List<Movie>> =_upcomingMovies

    private val _nowPlayingMovies=MutableLiveData<List<Movie>>()
    val nowPlayingMovies:LiveData<List<Movie>> =_nowPlayingMovies



     fun getPopularMovies(){
        viewModelScope.launch {
            val moviesResponse = repo.getPopularMovies(1)
            val movies = moviesResponse.results.map { it.toMovie() }
            _popularMovies.postValue(movies)
        }
    }

    fun getTopRatedMovies(){
        viewModelScope.launch {
            val moviesResponse = repo.getTopRatedMovies(1)
            val movies = moviesResponse.results.map { it.toMovie() }
            _topRatedMovies.postValue(movies)
        }
    }

    fun getUpcomingMovies(){
        viewModelScope.launch {
            val moviesResponse = repo.getUpComingMovies(1)
            val movies = moviesResponse.results.map { it.toMovie() }
            _upcomingMovies.postValue(movies)
        }
    }

    fun getNowPlayingMovies(){
        viewModelScope.launch {
            val moviesResponse = repo.getNowPlayingMovies(1)
            val movies = moviesResponse.results.map { it.toMovie() }
            _nowPlayingMovies.postValue(movies)
        }
    }

}