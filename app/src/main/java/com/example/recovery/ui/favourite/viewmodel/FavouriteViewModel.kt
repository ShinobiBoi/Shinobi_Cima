package com.example.recovery.ui.favourite.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.ui.favourite.repo.FavouriteRepoInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModel(private val repo: FavouriteRepoInterface) :ViewModel() {

    private val _favMovies = MutableLiveData<List<FavMovie>>()
    val favMovies: LiveData<List<FavMovie>> = _favMovies



    fun getFavMovie() {
        viewModelScope.launch(Dispatchers.Default){
            _favMovies.postValue(repo.getMovies())
        }
    }

}