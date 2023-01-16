package com.example.moviestask.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviestask.MainActivity
import com.example.moviestask.R
import com.example.moviestask.adapter.MovieAdapter
import com.example.moviestask.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_online_movies.*

class OfflineMoviesFragment : Fragment(R.layout.fragment_offline_movies) {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel = (activity as MainActivity).movieViewModel
        setUpRecyclerView()

        movieViewModel.getOfflineMovies().observe(viewLifecycleOwner, Observer { movies->
            movieAdapter.setMovieList(movies)
        })
    }

    private fun setUpRecyclerView() {
        movieAdapter = MovieAdapter()
        rvOnlineMovies.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}