package com.example.recovery.ui.upcoming.repo

import com.example.recovery.data.model.movie.MoviesList

interface UpComingRepoInterface {
    suspend fun getUpComingMovies(page:Int): MoviesList
}