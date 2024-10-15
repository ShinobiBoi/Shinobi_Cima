package com.example.recovery.utilites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recovery.R
import com.example.recovery.data.model.movie.Movie

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"


class NowPlayingAdapter (private val movies:List<Movie>) : RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.nowplaying_movie,parent,false)
        return NowPlayingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {

        val movie=movies.get(0)

        Glide.with(holder.movieImg).load(IMAGE_BASE_URL+movie.backdrop_path).into(holder.movieImg)
        Glide.with(holder.moviePoster).load(IMAGE_BASE_URL+movie.poster_path).into(holder.moviePoster)

    }



    inner class NowPlayingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val movieImg : ImageView = itemView.findViewById(R.id.now_playing_movieImage)
        val moviePoster : ImageView =itemView.findViewById(R.id.poster)

    }
}