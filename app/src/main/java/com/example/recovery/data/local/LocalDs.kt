package com.example.recovery.data.local

import android.content.Context
import android.util.Log
import com.example.recovery.data.model.entity.FavMovie

class LocalDs (context: Context):LocalDsInterface {

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

    override suspend fun getMovies(): List<FavMovie> {
        return movieDAO.getFavMovies()
    }
}