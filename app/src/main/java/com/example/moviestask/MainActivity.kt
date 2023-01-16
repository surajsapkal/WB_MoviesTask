package com.example.moviestask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.moviestask.databinding.ActivityMainBinding
import com.example.moviestask.db.MovieDatabase
import com.example.moviestask.repository.MovieRepository
import com.example.moviestask.viewmodel.MovieViewModel
import com.example.moviestask.viewmodel.MovieViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var movieViewModel: MovieViewModel
    private lateinit var movieRepository: MovieRepository
    private lateinit var movieViewModelProviderFactory: MovieViewModelProviderFactory

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieRepository = MovieRepository(MovieDatabase(this))
        movieViewModelProviderFactory = MovieViewModelProviderFactory(application,movieRepository)
        movieViewModel = ViewModelProvider(this,movieViewModelProviderFactory)[MovieViewModel::class.java]

        bottomNavigationView.setupWithNavController(moviesNavHostFragment.findNavController())

    }
}