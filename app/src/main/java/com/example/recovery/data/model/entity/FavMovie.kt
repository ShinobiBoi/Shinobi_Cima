package com.example.recovery.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class FavMovie(
    val backdrop_path: String,
    val poster_path: String,
    val original_title: String,
    val original_language: String,
    val runtime: Int,
    val vote_average: Double,
    val overview: String,
    val release_date: String,
    @PrimaryKey
    val id:Int) :Serializable
