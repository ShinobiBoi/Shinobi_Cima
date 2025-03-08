package com.example.recovery.ui.upcoming.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recovery.domain.repository.UpComingRepoInterface

class UpComingViewModelFactory(private val upComingRepoInterface: UpComingRepoInterface) :ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpComingViewModel::class.java))
            return UpComingViewModel(upComingRepoInterface) as T
        else
            throw IllegalArgumentException("Unknown Popular ViewModel class")
    }
}