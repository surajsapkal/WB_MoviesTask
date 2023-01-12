package com.example.moviestask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.moviestask.adapter.MovieAdapter
import com.example.moviestask.api.MoviesApi
import com.example.moviestask.const.Constant.Companion.API_KEY
import com.example.moviestask.databinding.ActivityMainBinding
import com.example.moviestask.repository.MovieRepository
import com.example.moviestask.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        movieViewModel = MovieViewModel()
        movieViewModel.getMovies(API_KEY)?.observe(this, Observer{
            Log.d(TAG,"Result is: $it")
            movieAdapter.setMovieList(it)
        })

    }

    private fun setupRecyclerView(){
        movieAdapter = MovieAdapter()
        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieAdapter
        }
    }
}