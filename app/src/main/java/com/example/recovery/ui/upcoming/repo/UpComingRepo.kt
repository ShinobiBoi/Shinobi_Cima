package com.example.recovery.ui.upcoming.repo

import com.example.recovery.data.model.movie.MoviesList
import com.example.recovery.domain.data.remote.RemoteDataSrc
import com.example.recovery.domain.repository.UpComingRepoInterface

class UpComingRepo (private val remoteDataSrc: RemoteDataSrc): UpComingRepoInterface {
    override suspend fun getUpComingMovies(page: Int): MoviesList {
        return remoteDataSrc.getUpComingMovies(page)
    }
}