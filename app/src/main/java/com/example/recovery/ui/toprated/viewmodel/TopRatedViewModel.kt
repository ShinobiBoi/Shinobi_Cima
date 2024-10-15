package com.example.recovery.ui.toprated.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.ui.toprated.repo.TopRatedRepoInterface
import kotlinx.coroutines.launch

class TopRatedViewModel(private val repo: TopRatedRepoInterface) :ViewModel(){

    private val _currentPage = MutableLiveData<Int>().apply { value = 1 } // Initialize with a default value
    val currentPage: LiveData<Int> = _currentPage

    private val _topRatedMovies=MutableLiveData<List<Movie>>()
    val topRatedMovies:LiveData<List<Movie>> = _topRatedMovies

    fun getTopRatedMovies(){
        viewModelScope.launch {
            val movies = repo.getTopRatedMovies(currentPage.value!!)
            _topRatedMovies.postValue(movies.results)

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