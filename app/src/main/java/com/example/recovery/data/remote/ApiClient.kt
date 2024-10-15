package com.example.recovery.data.remote

import com.example.recovery.data.model.cast.CrewList
import com.example.recovery.data.model.detailmovie.DetailMovie
import com.example.recovery.data.model.movie.MoviesList

object ApiClient : RemoteDataSrc {

    override suspend fun getPopularMovies(page: Int): MoviesList {
        return RetrofitHelper.retrofitServices.getPopularMovies(page)
    }

    override suspend fun getTopRatedMovies(page: Int): MoviesList {
        return RetrofitHelper.retrofitServices.getTopRatedMovies(page)
    }

    override suspend fun getUpComingMovies(page: Int): MoviesList {
        return RetrofitHelper.retrofitServices.getUpComingMovies(page)
    }

    override suspend fun getNowPlayingMovies(page: Int): MoviesList {
        return RetrofitHelper.retrofitServices.getNowPlayingMovies(page)
    }

    override suspend fun getSimilarMovies(id: String, page: Int): MoviesList {
        return RetrofitHelper.retrofitServices.getSimilarMovies(id,page)
    }

    override suspend fun getDetailMovie(id: String, page: Int) :DetailMovie {
        return RetrofitHelper.retrofitServices.getDetailMovie(id,page)
    }

    override suspend fun searchMovie(query: String, page: Int): MoviesList {
        return RetrofitHelper.retrofitServices.searchMovie(query,page)
    }

    override suspend fun getMovieCast(id: String, page: Int): CrewList {
        return RetrofitHelper.retrofitServices.getMovieCast(id,page)
    }
}