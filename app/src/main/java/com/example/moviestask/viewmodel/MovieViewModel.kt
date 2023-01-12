package com.example.moviestask.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviestask.api.MoviesApi
import com.example.moviestask.data.Movies
import com.example.moviestask.repository.MovieRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel() : ViewModel() {
    /*var result: MutableLiveData<List<com.example.moviestask.data.Result>>?= null

    fun getMovies(api_key : String){
        result = movieRepository.getMovies(api_key)
    }*/

    fun getMovies(api_key : String) : MutableLiveData<List<com.example.moviestask.data.Result>>{
        val data = MutableLiveData<List<com.example.moviestask.data.Result>>()

        val moviesApi = MoviesApi.getMoviesInstance()
        moviesApi.getMoviesList(api_key).enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                val response = response.body()
                if (response != null){
                    data.value = response.results
                }else{
                    data.value = null
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                data.value = null
            }

        })

        return data
    }
}