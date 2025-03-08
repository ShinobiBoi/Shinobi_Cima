package com.example.recovery.domain.repository

import com.example.recovery.data.model.movie.MoviesList


interface HomeRepoInterface {

    suspend fun getPopularMovies(page:Int) : MoviesList

    suspend fun getTopRatedMovies(page:Int): MoviesList

    suspend fun getUpComingMovies(page:Int): MoviesList

    suspend fun getNowPlayingMovies(page:Int): MoviesList
}