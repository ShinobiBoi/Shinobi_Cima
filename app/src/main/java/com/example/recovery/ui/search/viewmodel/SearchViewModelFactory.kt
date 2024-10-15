package com.example.recovery.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recovery.ui.search.repo.SearchRepoInterface

class SearchViewModelFactory(private val repoInterface: SearchRepoInterface) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SearchViewModel::class.java))
            return SearchViewModel(repoInterface) as T
        else
            throw IllegalArgumentException("Unknown Search ViewModel class")
    }
}