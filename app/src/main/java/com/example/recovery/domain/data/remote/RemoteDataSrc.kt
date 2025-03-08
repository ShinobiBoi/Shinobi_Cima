package com.example.recovery.domain.data.remote

import com.example.recovery.data.model.cast.CrewList
import com.example.recovery.data.model.detailmovie.DetailMovie
import com.example.recovery.data.model.detailmovie.DetailMovieResponse
import com.example.recovery.data.model.movie.MoviesList


interface RemoteDataSrc {

    suspend fun getPopularMovies(page:Int) : MoviesList

    suspend fun getTopRatedMovies(page:Int): MoviesList

    suspend fun getUpComingMovies(page:Int): MoviesList

    suspend fun getNowPlayingMovies(page:Int): MoviesList

    suspend fun getSimilarMovies(id:String,page:Int): MoviesList

    suspend fun getDetailMovie(id:String, page:Int) :DetailMovieResponse

    suspend fun searchMovie(query: String, page:Int):MoviesList

    suspend fun getMovieCast( id:String, page:Int): CrewList
}