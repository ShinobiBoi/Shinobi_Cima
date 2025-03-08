package com.example.recovery.domain.repository

import com.example.recovery.data.model.movie.MoviesList

interface TopRatedRepoInterface {
   suspend fun getTopRatedMovies( page:Int) : MoviesList
}