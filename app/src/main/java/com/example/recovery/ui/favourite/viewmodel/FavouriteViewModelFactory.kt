package com.example.recovery.ui.favourite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recovery.domain.repository.FavouriteRepoInterface

class FavouriteViewModelFactory(private val repo: FavouriteRepoInterface) :ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteViewModel::class.java))
            return FavouriteViewModel(repo) as T
        else
            throw IllegalArgumentException("Unknown Favourite ViewModel class")
    }
}