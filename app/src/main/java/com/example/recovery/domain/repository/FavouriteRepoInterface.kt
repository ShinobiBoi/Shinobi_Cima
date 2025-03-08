package com.example.recovery.domain.repository

import androidx.lifecycle.LiveData
import com.example.recovery.data.model.entity.FavMovie

interface FavouriteRepoInterface {

     fun getMovies(): LiveData<List<FavMovie>>

}