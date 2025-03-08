package com.example.recovery.ui.popular.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recovery.domain.repository.PopularRepoInterface

class PopularViewModelFactory (private val repoInterface: PopularRepoInterface) :ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopularViewModel::class.java)){
            return PopularViewModel(repoInterface) as T
        }
        throw IllegalArgumentException("Unknown Popular ViewModel class")
    }
}