package com.example.recovery.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.data.model.movie.toMovie
import com.example.recovery.domain.repository.SearchRepoInterface
import kotlinx.coroutines.launch

class SearchViewModel(private val repo: SearchRepoInterface) :ViewModel() {

    private val _movies= MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> =_movies

    fun getMovies(query: String){
        viewModelScope.launch {
            val moviesResponse = repo.searchMovie(query,1)
            val movies = moviesResponse.results.map { it.toMovie() }
            _movies.postValue(movies)
        }
    }

    fun getPopularMovies(){
        viewModelScope.launch {
            val moviesResponse = repo.getPopularMovies()
            val movies = moviesResponse.results.map { it.toMovie() }
            _movies.postValue(movies)

        }
    }

}