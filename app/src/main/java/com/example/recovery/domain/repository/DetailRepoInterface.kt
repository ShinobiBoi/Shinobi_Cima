package com.example.recovery.domain.repository

import androidx.lifecycle.LiveData
import com.example.recovery.data.model.cast.CrewList
import com.example.recovery.data.model.detailmovie.DetailMovieResponse
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.data.model.movie.MoviesList


interface DetailRepoInterface {


    suspend fun getSimilarMovies(id:String,page:Int): MoviesList

    suspend fun getDetailMovie(id:String, page:Int):DetailMovieResponse

    suspend fun getMovieCast(id:String, page:Int): CrewList



    suspend fun insertFavMovie(favMovie: FavMovie)

    suspend fun deleteFavMovie(favMovie: FavMovie)

    fun getFavMovies(): LiveData<List<FavMovie>>
}