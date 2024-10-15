package com.example.recovery.ui.popular.repo

import com.example.recovery.data.model.movie.MoviesList

interface PopularRepoInterface {
    suspend fun getPopularMovies(page:Int) : MoviesList
}