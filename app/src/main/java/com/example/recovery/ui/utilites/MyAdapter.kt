package com.example.recovery.ui.utilites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.databinding.MovieItemBinding


class MyAdapter(private val onMovieClick: (Movie) -> Unit) :
    ListAdapter<Movie, MyAdapter.MyViewHolder>(
        MovieDiffCallback()
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }


    inner class MyViewHolder(val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movie = movie
            binding.root.setOnClickListener() {
                onMovieClick(movie)
            }
            binding.executePendingBindings()

        }

    }


}