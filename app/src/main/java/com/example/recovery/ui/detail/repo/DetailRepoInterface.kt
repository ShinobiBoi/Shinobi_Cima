package com.example.recovery.ui.detail.repo

import com.example.recovery.data.model.cast.CrewList
import com.example.recovery.data.model.detailmovie.DetailMovie
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.data.model.movie.MoviesList
import com.example.recovery.data.remote.ApiServices.Companion.api_key



interface DetailRepoInterface {


    suspend fun getSimilarMovies(id:String,page:Int): MoviesList

    suspend fun getDetailMovie(id:String, page:Int):DetailMovie

    suspend fun getMovieCast(id:String, page:Int): CrewList



    suspend fun insertFavMovie(favMovie: FavMovie)

    suspend fun deleteFavMovie(favMovie: FavMovie)

    suspend fun getFavMovies():List<FavMovie>
}