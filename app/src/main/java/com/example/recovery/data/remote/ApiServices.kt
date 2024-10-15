package com.example.recovery.data.remote

import com.example.recovery.data.model.cast.CrewList
import com.example.recovery.data.model.detailmovie.DetailMovie
import com.example.recovery.data.model.movie.MoviesList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {


    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page:Int,
        @Query("api_key") key:String= api_key
    ): MoviesList

    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page:Int,
        @Query("api_key") key:String= api_key
    ): MoviesList

    @GET("3/movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("page") page:Int,
        @Query("api_key") key:String= api_key
    ): MoviesList

    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page:Int,
        @Query("api_key") key:String= api_key
    ): MoviesList

    @GET("3/movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") id:String,
        @Query("page") page:Int,
        @Query("api_key") key:String= api_key
    ): MoviesList

    @GET("3/movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") id:String,
        @Query("page") page:Int,
        @Query("api_key") key:String= api_key
    ):DetailMovie



    @GET("3/search/movie")
    suspend fun searchMovie(
        @Query("query")query: String,
        @Query("page") page:Int,
        @Query("api_key") key:String= api_key
    ):MoviesList


    @GET("3/movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") id:String,
        @Query("page") page:Int,
        @Query("api_key") key:String= api_key
    ):CrewList


    companion object{
        const val api_key="0e00cb8df334c216bc341e703723c22a"

    }

}

//https://api.themoviedb.org/3/movie/popular?page=2&api_key=0e00cb8df334c216bc341e703723c22a