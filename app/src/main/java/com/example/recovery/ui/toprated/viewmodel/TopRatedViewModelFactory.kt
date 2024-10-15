package com.example.recovery.ui.toprated.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recovery.ui.toprated.repo.TopRatedRepoInterface

class TopRatedViewModelFactory (private val topRatedRepoInterface: TopRatedRepoInterface) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TopRatedViewModel::class.java))
            return TopRatedViewModel(topRatedRepoInterface) as T
        else
            throw IllegalArgumentException("Unknown Popular ViewModel class")

    }

}