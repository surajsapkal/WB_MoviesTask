package com.example.moviestask.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviestask.MainActivity
import com.example.moviestask.R
import com.example.moviestask.adapter.MovieAdapter
import com.example.moviestask.const.Resource
import com.example.moviestask.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_online_movies.*

class OnlineMoviesFragment : Fragment(R.layout.fragment_online_movies) {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter

    val TAG = "Online News Fragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel = (activity as MainActivity).movieViewModel
        setUpRecyclerView()

        movieViewModel.movies.observe(viewLifecycleOwner, Observer{response->
            when(response){
                is Resource.Success->{
                    progressBar.visibility = View.INVISIBLE
                    response.data?.let { movieResponse->
                        movieAdapter.setMovieList(movieResponse.results.toList())
                        movieViewModel.savedMovie(movieResponse)
                    }
                }
                is Resource.Error->{
                    progressBar.visibility = View.INVISIBLE
                    response.message?.let { message->
                        Log.e(TAG,"An error occurred: $message")
                        Toast.makeText(activity,"An error occurred: $message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading->{
                    progressBar.visibility = View.VISIBLE
                }
            }

        })
    }

    private fun setUpRecyclerView(){
        movieAdapter = MovieAdapter()
        rvOnlineMovies.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}