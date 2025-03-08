package com.example.recovery.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.recovery.data.model.entity.FavMovie


@Dao
interface MovieDAO {

    @Upsert
    suspend fun insertMovie(favMovie: FavMovie)

    @Delete
    suspend fun deleteFavMeal(favMovie: FavMovie)

    @Query("SELECT * FROM favmovie")
    fun getFavMovies():LiveData<List<FavMovie>>



}