package com.example.recovery.ui.upcoming.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.data.model.movie.toMovie
import com.example.recovery.domain.repository.UpComingRepoInterface
import kotlinx.coroutines.launch

class UpComingViewModel(private val repo: UpComingRepoInterface) :ViewModel() {

    private val _currentPage = MutableLiveData<Int>().apply { value = 1 } // Initialize with a default value
    val currentPage: LiveData<Int> = _currentPage

    private val _upComingMovies= MutableLiveData<List<Movie>>()
    val upComingMovies:LiveData<List<Movie>> = _upComingMovies

    fun getUpComingMovies(){
        viewModelScope.launch {
            val moviesResponse=repo.getUpComingMovies(currentPage.value!!)
            val movies = moviesResponse.results.map { it.toMovie() }
            _upComingMovies.postValue(movies)
        }
    }

    fun nextPage(){
        _currentPage.value= _currentPage.value!! +1
        getUpComingMovies()
    }

    fun previousPage(){
        _currentPage.value= _currentPage.value!! -1
        getUpComingMovies()
    }

}