package com.example.recovery.ui.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recovery.ui.detail.repo.DetailRepoInterface

class DetailViewModelFactory(private val repo: DetailRepoInterface) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java))
            return DetailViewModel(repo) as T
        else
            throw IllegalArgumentException("Unknown Home ViewModel class")
    }

}