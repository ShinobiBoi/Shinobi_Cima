package com.example.recovery.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recovery.domain.repository.HomeRepoInterface

class HomeViewFactory(private val homeRepoInterface: HomeRepoInterface) :ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(homeRepoInterface) as T
        }
        throw IllegalArgumentException("Unknown Home ViewModel class")
    }
}