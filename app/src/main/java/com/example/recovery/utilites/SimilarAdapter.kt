package com.example.recovery.utilites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recovery.R
import com.example.recovery.data.model.movie.Movie

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
class SimilarAdapter(private val movies:List<Movie>,private val similarClickHandler: SimilarClickHandler) :
    RecyclerView.Adapter<SimilarAdapter.SimilarViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.detail_item,parent,false)
        return SimilarViewHolder(view)
    }

    override fun getItemCount(): Int {
    return movies.size
    }

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
       val movie=movies[position]

        if (movie.poster_path.isNullOrEmpty()){
            Glide.with(holder.movieImg).load(R.drawable.noimage).into(holder.movieImg)
        }else
            Glide.with(holder.movieImg).load(IMAGE_BASE_URL+movie.poster_path).into(holder.movieImg)


        holder.movie.setOnClickListener(){
            similarClickHandler.onSimilarMovieClick(movie.id)
        }

    }


    inner class SimilarViewHolder(val movie: View) : RecyclerView.ViewHolder(movie){
        val movieImg : ImageView = movie.findViewById(R.id.detail_item_image)

    }
}