package com.example.recovery.data.remote

import com.example.recovery.data.model.cast.CrewList
import com.example.recovery.data.model.detailmovie.DetailMovie
import com.example.recovery.data.model.movie.MoviesList
import com.example.recovery.data.remote.ApiServices.Companion.api_key
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RemoteDataSrc {

    suspend fun getPopularMovies(page:Int) : MoviesList

    suspend fun getTopRatedMovies(page:Int): MoviesList

    suspend fun getUpComingMovies(page:Int): MoviesList

    suspend fun getNowPlayingMovies(page:Int): MoviesList

    suspend fun getSimilarMovies(id:String,page:Int): MoviesList

    suspend fun getDetailMovie(id:String, page:Int) :DetailMovie

    suspend fun searchMovie(query: String, page:Int):MoviesList

    suspend fun getMovieCast( id:String, page:Int): CrewList
}