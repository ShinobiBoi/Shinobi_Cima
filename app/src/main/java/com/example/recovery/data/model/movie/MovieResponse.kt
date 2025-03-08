package com.example.recovery.data.model.movie

import com.example.recovery.R

data class MovieResponse(
val backdrop_path: String?,
val id: Int,
val original_language: String?,
val original_title: String?,
val overview: String?,
val poster_path: String?,
val release_date: String?,
val vote_average: Double?,
val runtime: Int?
)

fun MovieResponse.toMovie(): Movie {
     return Movie(
         id = this.id,
         original_title = this.original_title ?: "", // Default if title is null
         original_language = this.original_language ?: "", // Default if language is null
         overview = this.overview ?: "", // Overview can be null
         release_date = this.release_date ?: "", // Default if release date is null
         poster_path = this.poster_path?: "", // Construct full URL if poster path exists
         backdrop_path = this.backdrop_path?: "", // Construct full URL if backdrop path exists
         vote_average = this.vote_average?: 0.0,// Vote average can be null
         runtime = this.runtime ?:0// Runtime can be null
    )
}
