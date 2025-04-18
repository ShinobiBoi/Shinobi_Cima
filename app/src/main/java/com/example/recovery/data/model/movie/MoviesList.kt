package com.example.recovery.data.model.movie

data class MoviesList(
    val page: Int,
    val results: List<MovieResponse>,
    val total_pages: Int,
    val total_results: Int
)