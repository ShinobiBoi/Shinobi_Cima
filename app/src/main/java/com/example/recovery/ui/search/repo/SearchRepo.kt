package com.example.recovery.ui.search.repo

import com.example.recovery.data.model.movie.MoviesList
import com.example.recovery.domain.data.remote.RemoteDataSrc
import com.example.recovery.domain.repository.SearchRepoInterface

class SearchRepo(private val remoteDataSrc: RemoteDataSrc): SearchRepoInterface {
    override suspend fun searchMovie(query: String, page: Int): MoviesList {
        return remoteDataSrc.searchMovie(query,page)
    }

    override suspend fun getPopularMovies(): MoviesList {
        return remoteDataSrc.getPopularMovies(1)
    }
}