package com.example.recovery.ui.popular.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.data.model.movie.toMovie
import com.example.recovery.domain.repository.PopularRepoInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val repo: PopularRepoInterface) :ViewModel(){

    private val _currentPage = MutableLiveData<Int>().apply { value = 1 } // Initialize with a default value
    val currentPage: LiveData<Int> = _currentPage

    private val _popularMovies= MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> =_popularMovies

    fun getPopularMovies(){
        viewModelScope.launch {
            val moviesResponse = repo.getPopularMovies(currentPage.value!!)
            val movies = moviesResponse.results.map { it.toMovie() }
            _popularMovies.postValue(movies)

        }
    }

    fun nextPage() {
        _currentPage.value = (_currentPage.value ?: 1) + 1
        getPopularMovies()
    }

    // Function to decrease the page number
    fun previousPage() {
        _currentPage.value = (_currentPage.value ?: 1) - 1
        getPopularMovies()
    }



}