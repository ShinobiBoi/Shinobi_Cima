package com.example.recovery.domain.repository

import com.example.recovery.data.model.movie.MoviesList

interface PopularRepoInterface {
    suspend fun getPopularMovies(page:Int) : MoviesList
}