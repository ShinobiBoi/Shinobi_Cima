package com.example.recovery.utilites

import android.text.Html
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

class GridAdapter (private val movies:ArrayList<Movie>,private val clickHandler: ClickHandler): RecyclerView.Adapter<GridAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.movie_grid_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie=movies[position]

        if (movie.poster_path.isNullOrEmpty()){
            Glide.with(holder.movieImg).load(R.drawable.noimage).into(holder.movieImg)
        }else
            Glide.with(holder.movieImg).load(IMAGE_BASE_URL+movie.poster_path).into(holder.movieImg)

        var htmlText=""
        if(movie.release_date.isNullOrEmpty()){
            htmlText = "<font color='#FFFFFF'>${movie.original_title})</font>"
            holder.movieName.text=Html.fromHtml(htmlText)
        }

        else{
             htmlText = "<font color='#FFFFFF'>${movie.original_title}</font>  <font color='#7986CB'>(${movie.release_date.split("-").get(0)})</font>"
            holder.movieName.text= Html.fromHtml(htmlText)
        }


        holder.movie.setOnClickListener(){
            clickHandler.onMovieClick(movie)
        }

    }


    inner class MyViewHolder(val movie: View) : RecyclerView.ViewHolder(movie){
        val movieImg : ImageView = movie.findViewById(R.id.grid_movieImage)
        val movieName : TextView =movie.findViewById(R.id.grid_movie_name)

    }


}