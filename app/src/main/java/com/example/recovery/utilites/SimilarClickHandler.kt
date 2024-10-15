package com.example.recovery.utilites

import com.example.recovery.data.model.movie.Movie

interface SimilarClickHandler {
    fun onSimilarMovieClick(id: Int)
}