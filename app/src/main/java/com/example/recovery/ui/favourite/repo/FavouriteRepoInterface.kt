package com.example.recovery.ui.favourite.repo

import com.example.recovery.data.model.entity.FavMovie

interface FavouriteRepoInterface {


    suspend fun getMovies():List<FavMovie>

}