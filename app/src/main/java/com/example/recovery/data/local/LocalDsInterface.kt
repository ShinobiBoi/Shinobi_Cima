package com.example.recovery.data.local

import com.example.recovery.data.model.entity.FavMovie

interface LocalDsInterface {

    suspend fun insertFavMovie(favMovie: FavMovie)

    suspend fun deleteFavMovie(favMovie: FavMovie)

    suspend fun getMovies():List<FavMovie>
}