package com.example.recovery.domain.repository

import com.example.recovery.data.model.movie.MoviesList

interface UpComingRepoInterface {
    suspend fun getUpComingMovies(page:Int): MoviesList
}