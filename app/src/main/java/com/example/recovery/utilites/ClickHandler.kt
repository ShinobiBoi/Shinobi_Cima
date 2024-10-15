package com.example.recovery.utilites

import com.example.recovery.data.model.movie.Movie

interface ClickHandler {
   fun onMovieClick(movie:Movie)
}