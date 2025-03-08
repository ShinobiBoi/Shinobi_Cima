package com.example.recovery.ui.favourite.repo

import androidx.lifecycle.LiveData
import com.example.recovery.domain.data.local.LocalDsInterface
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.domain.repository.FavouriteRepoInterface

class FavouriteRepo( private val localDs: LocalDsInterface) : FavouriteRepoInterface {

    override  fun getMovies(): LiveData<List<FavMovie>> {
        return localDs.getMovies()
    }
}