package com.example.recovery.ui.toprated.repo

import com.example.recovery.data.model.movie.MoviesList

interface TopRatedRepoInterface {
   suspend fun getTopRatedMovies( page:Int) : MoviesList
}