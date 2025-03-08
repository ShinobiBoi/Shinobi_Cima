package com.example.recovery.ui.detail.repo

import androidx.lifecycle.LiveData
import com.example.recovery.domain.data.local.LocalDsInterface
import com.example.recovery.data.model.cast.CrewList
import com.example.recovery.data.model.detailmovie.DetailMovieResponse
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.data.model.movie.MoviesList
import com.example.recovery.domain.data.remote.RemoteDataSrc
import com.example.recovery.domain.repository.DetailRepoInterface

class DetailRepo (private val remoteDataSrc: RemoteDataSrc, private val localDs: LocalDsInterface) :
    DetailRepoInterface {
    override suspend fun getSimilarMovies(id: String, page: Int): MoviesList {
        return remoteDataSrc.getSimilarMovies(id,page)
    }

    override suspend fun getDetailMovie(id: String, page: Int):DetailMovieResponse {
        return remoteDataSrc.getDetailMovie(id,page)
    }

    override suspend fun getMovieCast(id: String, page: Int): CrewList {
        return remoteDataSrc.getMovieCast(id,page)
    }

    override suspend fun insertFavMovie(favMovie: FavMovie) {
        localDs.insertFavMovie(favMovie)
    }

    override suspend fun deleteFavMovie(favMovie: FavMovie) {
       localDs.deleteFavMovie(favMovie)
    }

    override  fun getFavMovies(): LiveData<List<FavMovie>> {
        return localDs.getMovies()
    }
}