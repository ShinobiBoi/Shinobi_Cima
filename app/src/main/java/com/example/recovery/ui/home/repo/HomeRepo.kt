package com.example.recovery.ui.home.repo

import com.example.recovery.data.model.movie.MoviesList
import com.example.recovery.domain.data.remote.RemoteDataSrc
import com.example.recovery.domain.repository.HomeRepoInterface

class HomeRepo(private val remoteDataSrc: RemoteDataSrc) : HomeRepoInterface {


    override suspend fun getPopularMovies(page: Int): MoviesList {
        return remoteDataSrc.getPopularMovies(page)
    }

    override suspend fun getTopRatedMovies(page: Int): MoviesList {
       return remoteDataSrc.getTopRatedMovies(page)
    }

    override suspend fun getUpComingMovies(page: Int): MoviesList {
       return remoteDataSrc.getUpComingMovies(page)
    }

    override suspend fun getNowPlayingMovies(page: Int): MoviesList {
        return remoteDataSrc.getNowPlayingMovies(page)
    }
}