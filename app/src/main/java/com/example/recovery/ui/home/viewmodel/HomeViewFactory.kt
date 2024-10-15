package com.example.recovery.ui.home.viewmodel

import android.widget.ViewSwitcher.ViewFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recovery.ui.home.repo.HomeRepoInterface

class HomeViewFactory(private val homeRepoInterface: HomeRepoInterface) :ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(homeRepoInterface) as T
        }
        throw IllegalArgumentException("Unknown Home ViewModel class")
    }
}