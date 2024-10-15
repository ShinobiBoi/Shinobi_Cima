package com.example.recovery.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recovery.data.model.entity.FavMovie

@Database(entities = [FavMovie::class], version = 1)
abstract class MovieDatabase :RoomDatabase(){

    abstract fun getMovieDao():MovieDAO

    companion object{

        @Volatile
        private  var instance: MovieDatabase? =null

        fun getInstance(context: Context): MovieDatabase {
            return instance ?: synchronized(this){

                val INSTANCE = Room.databaseBuilder(context, MovieDatabase::class.java,"movie_db")
                    .build()
                instance = INSTANCE
                return INSTANCE
            }
        }

    }

}