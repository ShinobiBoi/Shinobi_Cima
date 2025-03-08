package com.example.recovery.data.local

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.domain.data.local.LocalDsInterface

class LocalDs (context: Context): LocalDsInterface {

    private var db:MovieDatabase

    private var movieDAO:MovieDAO

    init {
        db=MovieDatabase.getInstance(context)
        movieDAO=db.getMovieDao()

    }

    override suspend fun insertFavMovie(favMovie: FavMovie) {
        movieDAO.insertMovie(favMovie)
    }

    override suspend fun deleteFavMovie(favMovie: FavMovie) {
        movieDAO.deleteFavMeal(favMovie)
    }

    override  fun getMovies(): LiveData<List<FavMovie>> {
        return movieDAO.getFavMovies()
    }
}