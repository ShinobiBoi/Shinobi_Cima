package com.example.recovery.ui.utilites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.databinding.DetailItemBinding

class SimilarAdapter(private val onSimilarMovieClick: (Movie) -> Unit) :
    ListAdapter<Movie, SimilarAdapter.SimilarViewHolder>(MovieDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        val binding = DetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimilarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    inner class SimilarViewHolder(val binding: DetailItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movie = movie
            binding.root.setOnClickListener{
                onSimilarMovieClick(movie)
            }
            binding.executePendingBindings()
        }

    }

}



