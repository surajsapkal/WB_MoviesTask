package com.example.moviestask.repository

import androidx.lifecycle.MutableLiveData
import com.example.moviestask.api.MoviesApi
import com.example.moviestask.data.Movies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository {

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