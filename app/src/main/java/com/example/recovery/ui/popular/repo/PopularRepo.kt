package com.example.recovery.ui.popular.repo

import com.example.recovery.data.model.movie.MoviesList
import com.example.recovery.data.remote.RemoteDataSrc

class PopularRepo(private val remoteDataSrc: RemoteDataSrc) :PopularRepoInterface {

    override suspend fun getPopularMovies(page: Int): MoviesList {
        return remoteDataSrc.getPopularMovies(page)
    }

}