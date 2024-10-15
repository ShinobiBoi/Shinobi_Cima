package com.example.recovery.ui.search.repo

import com.example.recovery.data.model.movie.MoviesList
import com.example.recovery.data.remote.RemoteDataSrc

class SearchRepo(private val remoteDataSrc: RemoteDataSrc):SearchRepoInterface {
    override suspend fun searchMovie(query: String, page: Int): MoviesList {
        return remoteDataSrc.searchMovie(query,page)
    }

    override suspend fun getPopularMovies(): MoviesList {
        return remoteDataSrc.getPopularMovies(1)
    }
}