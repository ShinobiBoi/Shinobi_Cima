package com.example.recovery.ui.toprated.repo

import com.example.recovery.data.model.movie.MoviesList
import com.example.recovery.data.remote.RemoteDataSrc

class TopRatedRepo(private val remoteDataSrc: RemoteDataSrc) :TopRatedRepoInterface {
    override suspend fun getTopRatedMovies(page: Int): MoviesList {
        return remoteDataSrc.getTopRatedMovies(page)
    }
}