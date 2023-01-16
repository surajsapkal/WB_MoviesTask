package com.example.moviestask.repository

import com.example.moviestask.api.RetrofitInstance
import com.example.moviestask.data.Results
import com.example.moviestask.db.MovieDatabase

class MovieRepository(
    private val db : MovieDatabase
) {

    suspend fun getMovies(api_key : String) = RetrofitInstance.api.getMoviesList(api_key)

    suspend fun upsert(result: Results) = db.getMovieDao().upsert(result)

    fun getOfflineMovies() = db.getMovieDao().getAllMovies()
}