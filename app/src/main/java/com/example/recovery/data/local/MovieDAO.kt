package com.example.recovery.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.data.model.movie.Movie

@Dao
interface MovieDAO {

    @Insert
    suspend fun insertMovie(favMovie: FavMovie)

    @Delete
    suspend fun deleteFavMeal(favMovie: FavMovie)

    @Query("SELECT * FROM favmovie")
    fun getFavMovies():List<FavMovie>

}