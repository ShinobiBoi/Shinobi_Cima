package com.example.recovery.ui.favourite.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recovery.R
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.data.model.movie.Movie

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

class FavouriteAdapter(private val onMovieClick:(Movie)->Unit): ListAdapter<FavMovie,FavouriteAdapter.FavouriteViewHolder>(FavMovieDiffCallback()) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.movie_grid_item,parent,false)
        return FavouriteViewHolder(view)
    }



    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val movie=getItem(position)

        Glide.with(holder.movieImg).load(IMAGE_BASE_URL +movie.poster_path).into(holder.movieImg)

        val htmlText = "<font color='#FFFFFF'>${movie.original_title}</font>  <font color='#7986CB'>(${movie.release_date.split("-").get(0)})</font>"
        holder.movieName.text= Html.fromHtml(htmlText)


        holder.movie.setOnClickListener(){
            onMovieClick(
                    Movie(movie.backdrop_path,movie.id,movie.original_language,movie.original_title,movie.overview
                    ,movie.poster_path,movie.release_date,movie.vote_average,movie.runtime)
                    )
        }
    }


    inner class FavouriteViewHolder(val movie: View) : RecyclerView.ViewHolder(movie){
        val movieImg : ImageView = movie.findViewById(R.id.grid_movieImage)
        val movieName : TextView =movie.findViewById(R.id.grid_movie_name)

    }

}

class FavMovieDiffCallback :DiffUtil.ItemCallback<FavMovie>(){
    override fun areItemsTheSame(oldItem: FavMovie, newItem: FavMovie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FavMovie, newItem: FavMovie): Boolean {
        return oldItem == newItem
    }

}
