package com.example.recovery.domain.data.local

import androidx.lifecycle.LiveData
import com.example.recovery.data.model.entity.FavMovie

interface LocalDsInterface {

    suspend fun insertFavMovie(favMovie: FavMovie)

    suspend fun deleteFavMovie(favMovie: FavMovie)

     fun getMovies(): LiveData<List<FavMovie>>
}