package com.example.recovery.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.ui.home.repo.HomeRepoInterface
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: HomeRepoInterface) :ViewModel() {

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
            val movies = repo.getPopularMovies(1)
            _popularMovies.postValue(movies.results)
        }
    }

    fun getTopRatedMovies(){
        viewModelScope.launch {
            val movies = repo.getTopRatedMovies(1)
            _topRatedMovies.postValue(movies.results)
        }
    }

    fun getUpcomingMovies(){
        viewModelScope.launch {
            val movies = repo.getUpComingMovies(1)
            _upcomingMovies.postValue(movies.results)
        }
    }

    fun getNowPlayingMovies(){
        viewModelScope.launch {
            val movies = repo.getNowPlayingMovies(1)
            _nowPlayingMovies.postValue(movies.results)
        }
    }

}