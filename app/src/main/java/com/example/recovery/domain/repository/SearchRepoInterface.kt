package com.example.recovery.domain.repository

import com.example.recovery.data.model.movie.MoviesList

interface SearchRepoInterface {
    suspend fun searchMovie(query: String, page:Int): MoviesList

    suspend fun getPopularMovies() : MoviesList
}