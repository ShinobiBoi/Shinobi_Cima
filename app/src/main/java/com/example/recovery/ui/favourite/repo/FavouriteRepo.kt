package com.example.recovery.ui.favourite.repo

import com.example.recovery.data.local.LocalDsInterface
import com.example.recovery.data.model.entity.FavMovie

class FavouriteRepo( private val localDs: LocalDsInterface) : FavouriteRepoInterface {

    override suspend fun getMovies(): List<FavMovie> {
        return localDs.getMovies()
    }
}