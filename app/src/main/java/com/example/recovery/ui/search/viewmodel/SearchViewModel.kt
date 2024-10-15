package com.example.recovery.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.ui.search.repo.SearchRepoInterface
import kotlinx.coroutines.launch

class SearchViewModel(private val repo: SearchRepoInterface) :ViewModel() {

    private val _movies= MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> =_movies

    fun getMovies(query: String){
        viewModelScope.launch {
            val m = repo.searchMovie(query,1)
            _movies.postValue(m.results)
        }
    }

    fun getPopularMovies(){
        viewModelScope.launch {
            val movies = repo.getPopularMovies()
            _movies.postValue(movies.results)

        }
    }

}