package com.example.recovery.data.model.detailmovie

data class DetailMovieResponse(
    val adult: Boolean?,
    val backdrop_path: String?,
    val belongs_to_collection: Any?,
    val budget: Int?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Int?,
    val imdb_id: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val revenue: Int?,
    val runtime: Int?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
)

fun DetailMovieResponse.toDetailMovie(): DetailMovie {
    return DetailMovie(
        adult = adult ?: false,  // Defaulting to false if null
        backdrop_path = backdrop_path ?: "",  // Defaulting to empty string if null
        belongs_to_collection = belongs_to_collection ?: Any(),  // Defaulting to empty object if null
        budget = budget ?: 0,  // Defaulting to 0 if null
        genres = genres ?: emptyList(),  // Defaulting to empty list if null
        homepage = homepage ?: "",  // Defaulting to empty string if null
        id = id ?: 0,  // Defaulting to 0 if null
        imdb_id = imdb_id ?: "",  // Defaulting to empty string if null
        original_language = original_language ?: "",  // Defaulting to empty string if null
        original_title = original_title ?: "",  // Defaulting to empty string if null
        overview = overview ?: "",  // Defaulting to empty string if null
        popularity = popularity ?: 0.0,  // Defaulting to 0.0 if null
        poster_path = poster_path ?: "",  // Defaulting to empty string if null
        release_date = release_date ?: "",  // Defaulting to empty string if null
        revenue = revenue ?: 0,  // Defaulting to 0 if null
        runtime = runtime ?: 0,  // Defaulting to 0 if null
        status = status ?: "",  // Defaulting to empty string if null
        tagline = tagline ?: "",  // Defaulting to empty string if null
        title = title ?: "",  // Defaulting to empty string if null
        video = video ?: false,  // Defaulting to false if null
        vote_average = vote_average ?: 0.0,  // Defaulting to 0.0 if null
        vote_count = vote_count ?: 0  // Defaulting to 0 if null
    )
}
