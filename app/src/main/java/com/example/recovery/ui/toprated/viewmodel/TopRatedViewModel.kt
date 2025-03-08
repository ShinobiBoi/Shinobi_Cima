package com.example.recovery.ui.toprated.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.data.model.movie.toMovie
import com.example.recovery.domain.repository.TopRatedRepoInterface
import kotlinx.coroutines.launch

class TopRatedViewModel(private val repo: TopRatedRepoInterface) :ViewModel(){

    private val _currentPage = MutableLiveData<Int>().apply { value = 1 } // Initialize with a default value
    val currentPage: LiveData<Int> = _currentPage

    private val _topRatedMovies=MutableLiveData<List<Movie>>()
    val topRatedMovies:LiveData<List<Movie>> = _topRatedMovies

    fun getTopRatedMovies(){
        viewModelScope.launch {
            val moviesResponse = repo.getTopRatedMovies(currentPage.value!!)
            val movies = moviesResponse.results.map { it.toMovie() }
            _topRatedMovies.postValue(movies)

        }
    }
    fun nextPage(){
        _currentPage.value= _currentPage.value!! +1
        getTopRatedMovies()
    }
    fun previousPage(){
        _currentPage.value =_currentPage.value!! -1
        getTopRatedMovies()
    }

}