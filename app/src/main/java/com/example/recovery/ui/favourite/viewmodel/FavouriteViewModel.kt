package com.example.recovery.ui.favourite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.domain.repository.FavouriteRepoInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repo: FavouriteRepoInterface) :ViewModel() {

    val favMovies: LiveData<List<FavMovie>> = repo.getMovies()

}