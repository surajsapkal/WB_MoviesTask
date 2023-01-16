package com.example.moviestask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviestask.const.Constant.Companion.IMAGE_BASE
import com.example.moviestask.data.Movie
import com.example.moviestask.databinding.MovieItemBinding

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var result = ArrayList<Movie>()
    fun setMovieList(result: List<Movie>){
        this.result = result as ArrayList<Movie>
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.apply {
            movieTitle.text = result[position].title
            movieReleaseDate.text = result[position].release_date
            Glide.with(holder.itemView).load(IMAGE_BASE + result[position].poster_path).into(moviePoster)
        }

    }

    override fun getItemCount(): Int {
        return result.size
    }
}